package vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap;

public final class DIContainer {
    private static DIContainer instance;
    private final IUserRepository userRepository;
    private final IUserService userService;

    private DIContainer() {
        this.userRepository = new UserRepository();
        this.userService = new UserService(userRepository);
    }

    public static DIContainer getInstance() {
        if (instance == null) {
            instance = new DIContainer();
        }
        return instance;
    }

    public IUserRepository getUserRepository() {
        return userRepository;
    }

    public IUserService getUserService() {
        return userService;
    }
}
