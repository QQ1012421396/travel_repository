package cn.lzy.domain;

/**
 * 结果信息实体类，用于封装后端返回前端数据对象
 */
public class ResultInfo {
    private boolean flag;//后端返回结果是否正确标志。成功：true  失败：false
    private String errorMsg;//发生异常的错误信息
    private Object resultObj;//后端返回结果数据对象
    public ResultInfo() {
    }

    public ResultInfo(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public void setResultObj(Object resultObj) {
        this.resultObj = resultObj;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
