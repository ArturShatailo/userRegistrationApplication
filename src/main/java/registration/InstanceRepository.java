package registration;

public interface InstanceRepository {

    /**
     * Instance of UserRepository class to general usage in servlet classes
     */
    UserRepository ur = new UserRepository();
}
