package info.marcussoftware.mschat;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
