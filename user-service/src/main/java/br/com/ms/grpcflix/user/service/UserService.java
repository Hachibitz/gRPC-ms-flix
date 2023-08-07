package br.com.ms.grpcflix.user.service;

import br.com.ms.grpc.common.Genre;
import br.com.ms.grpc.user.UserGenreUpdateRequest;
import br.com.ms.grpc.user.UserResponse;
import br.com.ms.grpc.user.UserSearchRequest;
import br.com.ms.grpc.user.UserServiceGrpc;
import br.com.ms.grpcflix.user.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder userBuilder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    userBuilder.setName(user.getName())
                            .setLoginId(user.getLogin())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });
        responseObserver.onNext(userBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder userBuilder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().toString());
                    userBuilder.setName(user.getName())
                            .setLoginId(user.getLogin())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });
        responseObserver.onNext(userBuilder.build());
        responseObserver.onCompleted();
    }
}
