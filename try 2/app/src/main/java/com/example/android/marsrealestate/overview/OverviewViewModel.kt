/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.network.ImgAttr
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

enum class MarsApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the response String
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<ImgAttr>>()

    val properties: LiveData<List<ImgAttr>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<ImgAttr>()
    val navigateToSelectedProperty: LiveData<ImgAttr>
        get() = _navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties("moon")
    }

    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved.
     */
    private fun getMarsRealEstateProperties(Value: String) {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                val listResult = MarsApi.retrofitService.getProperties(Value)
                val lphoto = listResult.awaitResponse().body()?.photos!!.photo
                _properties.value = lphoto
                _status.value = MarsApiStatus.DONE

                /*val item = lphoto[2]
                "https://farm${item.farm}.staticflickr.com/${item.server}/${item.id}_${item.secret}.jpg"*/


            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun displayPropertyDetails(imgAttr: ImgAttr) {
        _navigateToSelectedProperty.value = imgAttr
    }
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
    fun search(text: String){
        getMarsRealEstateProperties(text)
    }
}
