package domain

interface RequestState<out T> {
    data object Idle : RequestState<Nothing>
    data object Loading : RequestState<Nothing>
    data class Success<T>(val data: T) : RequestState<T>
    data class Failure(val message: String) : RequestState<Nothing>

    fun isLoading(): Boolean {
        return this is Loading
    }

    fun isFailure(): Boolean {
        return this is Failure
    }

    fun isSuccess(): Boolean {
        return this is Success
    }

    fun getSuccessData(): T? {
        return if (this is Success) {
            this.data
        } else {
            null
        }
    }

    fun getFailureMessage(): String? {
        return if(this is Failure) {
            this.message
        }
        else {
            null
        }
    }
}
