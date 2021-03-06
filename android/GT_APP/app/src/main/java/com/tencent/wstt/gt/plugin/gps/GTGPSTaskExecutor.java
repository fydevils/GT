/*
 * Tencent is pleased to support the open source community by making
 * Tencent GT (Version 2.4 and subsequent versions) available.
 *
 * Notwithstanding anything to the contrary herein, any previous version
 * of Tencent GT shall not be subject to the license hereunder.
 * All right, title, and interest, including all intellectual property rights,
 * in and to the previous version of Tencent GT (including any and all copies thereof)
 * shall be owned and retained by Tencent and subject to the license under the
 * Tencent GT End User License Agreement (http://gt.qq.com/wp-content/EULA_EN.html).
 * 
 * Copyright (C) 2015 THL A29 Limited, a Tencent company. All rights reserved.
 * 
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://opensource.org/licenses/MIT
 * 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.tencent.wstt.gt.plugin.gps;

import com.tencent.wstt.gt.plugin.PluginManager;
import com.tencent.wstt.gt.plugin.PluginTaskExecutor;

import android.content.Intent;
import android.os.Bundle;

public class GTGPSTaskExecutor implements PluginTaskExecutor {
	private static GTGPSTaskExecutor INSTANCE;

	private GTGPSTaskExecutor() {

	}

	public static GTGPSTaskExecutor getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new GTGPSTaskExecutor();
		}
		return INSTANCE;
	}

	/**
	 * 用于接收SDK端发来的对GPS插件的控制
	 */
	@Override
	public void execute(Bundle bundle) {

		String cmd = bundle.getString("cmd");
		if (cmd != null && cmd.equals("replay")) {
			int seq = bundle.getInt("seq", -1);
			String item = bundle.getString("filename");
			// 起Replay服务
			if (seq >= 0) {
				Intent intent = new Intent();
				intent.putExtra("seq", seq);
				PluginManager.getInstance().getPluginControler(
						).startService(GTGPSReplayEngine.getInstance(), intent);
			}
			else if (item != null)
			{
				Intent intent = new Intent();
				intent.putExtra("filename", item);
				PluginManager.getInstance().getPluginControler(
						).startService(GTGPSReplayEngine.getInstance(), intent);
			}
				
		} else if (cmd != null && cmd.equals("stopReplay")) {
			// 停Replay服务
			PluginManager.getInstance().getPluginControler(
					).stopService(GTGPSReplayEngine.getInstance());
		}
		else if  (cmd != null && cmd.equals("record")) {
			String item = bundle.getString("filename");
			// 起Record服务
			if (item == null)
			{
				PluginManager.getInstance().getPluginControler(
						).startService(GTGPSRecordEngine.getInstance());
			}
			else
			{
				Intent intent = new Intent();
				intent.putExtra("filename", item);
				PluginManager.getInstance().getPluginControler(
						).startService(GTGPSRecordEngine.getInstance(), intent);
			}
		}
		else if  (cmd != null && cmd.equals("stopRecord")) {
			// 停Record服务
			PluginManager.getInstance().getPluginControler(
					).stopService(GTGPSRecordEngine.getInstance());
		}	
	}

}
