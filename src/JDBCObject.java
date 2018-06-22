import java.util.List;

public class JDBCObject {
    public void save(JDBCDao.SaveListerner listener) {
        JDBCHelper helper = JDBCHelper.getInstance();
        try {
            int result = helper.save(this);
            if (result > 0) {
                listener.onSucceed();
            } else {
                listener.onFailed(new Exception("save failed!"));
            }
        } catch (Exception e) {
            listener.onFailed(e);
        }
    }

    public void delete(JDBCDao.DeleteListener listener) {
        JDBCHelper helper = JDBCHelper.getInstance();
        try {
            int result = helper.delete(this);
            if (result > 0) {
                listener.onSucceed();
            } else {
                listener.onFailed(new Exception("delete failed!"));
            }
        } catch (Exception e) {
            listener.onFailed(e);
        }
    }

    public void update(Object condition,JDBCDao.UpdateListener listener) {
        JDBCHelper helper = JDBCHelper.getInstance();
        try {
            int result = helper.update(this, condition);
            if (result > 0) {
                listener.onSucceed();
            } else {
                listener.onFailed(new Exception("update failed!"));
            }
        } catch (Exception e) {
            listener.onFailed(e);
        }
    }

    public <T> void query(Class<T> cls, JDBCDao.QueryListener<T> listener) {
        JDBCHelper helper = JDBCHelper.getInstance();
        try {
            List<T> resultList = helper.query(this, cls);

            if (resultList != null) {
                listener.onSucceed(resultList);
            } else {
                listener.onFailed(new Exception("query failed!"));
            }
        } catch (Exception e) {
            listener.onFailed(e);
        }
    }
}
