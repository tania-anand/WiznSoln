package githubrepo.com.githubrepo.Interface;

import java.util.ArrayList;

/**
 * Created by sony on 07-Jan-16.
 */
public interface MyResponseListener {
    void onMyResponse(boolean success, ArrayList<Object> arrayList, String collectionName);
    void onMyResponse(boolean flag);
}
