package tw.teng.hw2.resource.network

interface OnApiListener<T> {
    fun onApiTaskSuccess(responseData: T)

    fun onApiTaskFailure(toString: String)
}
