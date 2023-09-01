package com.tiphubapps.ax.domain.repository

/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class UseCaseResult<out R> {

    data class UseCaseSuccess<out T>(val data: T) : UseCaseResult<T>()
    data class UseCaseError(val exception: Exception) : UseCaseResult<Nothing>()
    public object Loading : UseCaseResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is UseCaseSuccess<*> -> "Success[data=$data]"
            is UseCaseError -> "Error[exception=$exception]"
            Loading -> "Loading"
            else -> "else"
        }
    }
}

/**
 * `true` if [Result] is of type [UseCaseSuccess] & holds non-null [UseCaseSuccess.data].
 */
val UseCaseResult<*>.succeeded
    get() = this is UseCaseResult.UseCaseSuccess && data != null