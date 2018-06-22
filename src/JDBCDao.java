import java.util.List;

public class JDBCDao {
    public interface SaveListerner{
        void onSucceed();

        void onFailed(Exception e);
    }

    public interface DeleteListener {
        void onSucceed();

        void onFailed(Exception e);
    }

    public interface UpdateListener {
        void onSucceed();

        void onFailed(Exception e);
    }

    public  interface  QueryListener <T>{
        void onSucceed(List<T> result);

        void onFailed(Exception e);
    }








}
