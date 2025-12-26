/*
 * Copyright (C) 2023 Paranoid Android
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
 */

package co.aospa.keyhandler

import android.content.Context
import android.hardware.input.InputManager
import android.view.KeyEvent
import android.util.Log

import com.android.internal.os.DeviceKeyHandler

class KeyHandler(
    private val context: Context
) : DeviceKeyHandler {

    private val inputManager = context.getSystemService(InputManager::class.java)
    private val fpDoubleTapHandler = FpDoubleTapHandler(context)

    override fun handleKeyEvent(event: KeyEvent): KeyEvent? {
        if (event.scanCode != KEYCODE_FP_DOUBLE_TAP_FPC &&
            event.scanCode != KEYCODE_FP_DOUBLE_TAP_GOODIX) {
            return event
        }

        val device = inputManager.getInputDevice(event.deviceId)
        if (device?.name != DEVICE_NAME_FPC && device?.name != DEVICE_NAME_GOODIX) {
            return event
        }

        Log.d(TAG, "fp double tap event: $event")
        fpDoubleTapHandler.handleEvent(event)
        return null
    }

    companion object {
        private const val TAG = "XiaomiKeyHandler"
        private const val KEYCODE_FP_DOUBLE_TAP_FPC = 106
        private const val KEYCODE_FP_DOUBLE_TAP_GOODIX = 306
        private const val DEVICE_NAME_FPC = "uinput-fpc"
        private const val DEVICE_NAME_GOODIX = "uinput-goodix"
    }

}
