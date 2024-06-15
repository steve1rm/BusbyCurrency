package domain

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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

@Composable
fun <T> RequestState<T>.DisplayResult(
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: (@Composable () -> Unit)? = null,
    onError: (@Composable (errorMessage: String?) -> Unit)? = null,
    onSuccess: (@Composable (data: T?) -> Unit)? = null,
    translationSpec: ContentTransform = scaleIn(tween(durationMillis = 400))
            + fadeIn(tween(durationMillis = 800))
            togetherWith scaleOut(tween(durationMillis = 400))
            + fadeOut(tween(durationMillis = 800))
) {
    AnimatedContent(
        targetState = this@DisplayResult,
        transitionSpec = {
            translationSpec
        },
        label = "Content Animation"
    ) { state ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            when (state) {
                is RequestState.Idle -> {
                    onIdle?.invoke()
                }
                is RequestState.Loading -> {
                    onLoading?.invoke()
                }
                is RequestState.Failure -> {
                    onError?.invoke(state.getFailureMessage())
                }
                is RequestState.Success -> {
                    onSuccess?.invoke(state.getSuccessData())
                }
            }
        }
    }
}
