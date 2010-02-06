/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBThreadModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the MBThread table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThreadImpl
 * @see       com.liferay.portlet.messageboards.model.MBThread
 * @see       com.liferay.portlet.messageboards.model.MBThreadModel
 * @generated
 */
public class MBThreadModelImpl extends BaseModelImpl<MBThread> {
	public static final String TABLE_NAME = "MBThread";
	public static final Object[][] TABLE_COLUMNS = {
			{ "threadId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "categoryId", new Integer(Types.BIGINT) },
			{ "rootMessageId", new Integer(Types.BIGINT) },
			{ "messageCount", new Integer(Types.INTEGER) },
			{ "viewCount", new Integer(Types.INTEGER) },
			{ "lastPostByUserId", new Integer(Types.BIGINT) },
			{ "lastPostDate", new Integer(Types.TIMESTAMP) },
			{ "priority", new Integer(Types.DOUBLE) },
			{ "status", new Integer(Types.INTEGER) },
			{ "statusByUserId", new Integer(Types.BIGINT) },
			{ "statusByUserName", new Integer(Types.VARCHAR) },
			{ "statusDate", new Integer(Types.TIMESTAMP) }
		};
	public static final String TABLE_SQL_CREATE = "create table MBThread (threadId LONG not null primary key,groupId LONG,categoryId LONG,rootMessageId LONG,messageCount INTEGER,viewCount INTEGER,lastPostByUserId LONG,lastPostDate DATE null,priority DOUBLE,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table MBThread";
	public static final String ORDER_BY_JPQL = " ORDER BY mbThread.priority DESC, mbThread.lastPostDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY MBThread.priority DESC, MBThread.lastPostDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.messageboards.model.MBThread"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBThread"),
			true);

	public static MBThread toModel(MBThreadSoap soapModel) {
		MBThread model = new MBThreadImpl();

		model.setThreadId(soapModel.getThreadId());
		model.setGroupId(soapModel.getGroupId());
		model.setCategoryId(soapModel.getCategoryId());
		model.setRootMessageId(soapModel.getRootMessageId());
		model.setMessageCount(soapModel.getMessageCount());
		model.setViewCount(soapModel.getViewCount());
		model.setLastPostByUserId(soapModel.getLastPostByUserId());
		model.setLastPostDate(soapModel.getLastPostDate());
		model.setPriority(soapModel.getPriority());
		model.setStatus(soapModel.getStatus());
		model.setStatusByUserId(soapModel.getStatusByUserId());
		model.setStatusByUserName(soapModel.getStatusByUserName());
		model.setStatusDate(soapModel.getStatusDate());

		return model;
	}

	public static List<MBThread> toModels(MBThreadSoap[] soapModels) {
		List<MBThread> models = new ArrayList<MBThread>(soapModels.length);

		for (MBThreadSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBThread"));

	public MBThreadModelImpl() {
	}

	public long getPrimaryKey() {
		return _threadId;
	}

	public void setPrimaryKey(long pk) {
		setThreadId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_threadId);
	}

	public long getThreadId() {
		return _threadId;
	}

	public void setThreadId(long threadId) {
		_threadId = threadId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCategoryId() {
		return _categoryId;
	}

	public void setCategoryId(long categoryId) {
		_categoryId = categoryId;
	}

	public long getRootMessageId() {
		return _rootMessageId;
	}

	public void setRootMessageId(long rootMessageId) {
		_rootMessageId = rootMessageId;
	}

	public int getMessageCount() {
		return _messageCount;
	}

	public void setMessageCount(int messageCount) {
		_messageCount = messageCount;
	}

	public int getViewCount() {
		return _viewCount;
	}

	public void setViewCount(int viewCount) {
		_viewCount = viewCount;
	}

	public long getLastPostByUserId() {
		return _lastPostByUserId;
	}

	public void setLastPostByUserId(long lastPostByUserId) {
		_lastPostByUserId = lastPostByUserId;
	}

	public String getLastPostByUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getLastPostByUserId(), "uuid",
			_lastPostByUserUuid);
	}

	public void setLastPostByUserUuid(String lastPostByUserUuid) {
		_lastPostByUserUuid = lastPostByUserUuid;
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getStatusByUserId(), "uuid",
			_statusByUserUuid);
	}

	public void setStatusByUserUuid(String statusByUserUuid) {
		_statusByUserUuid = statusByUserUuid;
	}

	public String getStatusByUserName() {
		return GetterUtil.getString(_statusByUserName);
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	public MBThread toEscapedModel() {
		if (isEscapedModel()) {
			return (MBThread)this;
		}
		else {
			MBThread model = new MBThreadImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setThreadId(getThreadId());
			model.setGroupId(getGroupId());
			model.setCategoryId(getCategoryId());
			model.setRootMessageId(getRootMessageId());
			model.setMessageCount(getMessageCount());
			model.setViewCount(getViewCount());
			model.setLastPostByUserId(getLastPostByUserId());
			model.setLastPostDate(getLastPostDate());
			model.setPriority(getPriority());
			model.setStatus(getStatus());
			model.setStatusByUserId(getStatusByUserId());
			model.setStatusByUserName(HtmlUtil.escape(getStatusByUserName()));
			model.setStatusDate(getStatusDate());

			model = (MBThread)Proxy.newProxyInstance(MBThread.class.getClassLoader(),
					new Class[] { MBThread.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(MBThread.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		MBThreadImpl clone = new MBThreadImpl();

		clone.setThreadId(getThreadId());
		clone.setGroupId(getGroupId());
		clone.setCategoryId(getCategoryId());
		clone.setRootMessageId(getRootMessageId());
		clone.setMessageCount(getMessageCount());
		clone.setViewCount(getViewCount());
		clone.setLastPostByUserId(getLastPostByUserId());
		clone.setLastPostDate(getLastPostDate());
		clone.setPriority(getPriority());
		clone.setStatus(getStatus());
		clone.setStatusByUserId(getStatusByUserId());
		clone.setStatusByUserName(getStatusByUserName());
		clone.setStatusDate(getStatusDate());

		return clone;
	}

	public int compareTo(MBThread mbThread) {
		int value = 0;

		if (getPriority() < mbThread.getPriority()) {
			value = -1;
		}
		else if (getPriority() > mbThread.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getLastPostDate(), mbThread.getLastPostDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MBThread mbThread = null;

		try {
			mbThread = (MBThread)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = mbThread.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{threadId=");
		sb.append(getThreadId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", categoryId=");
		sb.append(getCategoryId());
		sb.append(", rootMessageId=");
		sb.append(getRootMessageId());
		sb.append(", messageCount=");
		sb.append(getMessageCount());
		sb.append(", viewCount=");
		sb.append(getViewCount());
		sb.append(", lastPostByUserId=");
		sb.append(getLastPostByUserId());
		sb.append(", lastPostDate=");
		sb.append(getLastPostDate());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append(", statusByUserId=");
		sb.append(getStatusByUserId());
		sb.append(", statusByUserName=");
		sb.append(getStatusByUserName());
		sb.append(", statusDate=");
		sb.append(getStatusDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(43);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.messageboards.model.MBThread");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>threadId</column-name><column-value><![CDATA[");
		sb.append(getThreadId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>categoryId</column-name><column-value><![CDATA[");
		sb.append(getCategoryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>rootMessageId</column-name><column-value><![CDATA[");
		sb.append(getRootMessageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageCount</column-name><column-value><![CDATA[");
		sb.append(getMessageCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>viewCount</column-name><column-value><![CDATA[");
		sb.append(getViewCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPostByUserId</column-name><column-value><![CDATA[");
		sb.append(getLastPostByUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPostDate</column-name><column-value><![CDATA[");
		sb.append(getLastPostDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserId</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserName</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusDate</column-name><column-value><![CDATA[");
		sb.append(getStatusDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _threadId;
	private long _groupId;
	private long _categoryId;
	private long _rootMessageId;
	private int _messageCount;
	private int _viewCount;
	private long _lastPostByUserId;
	private String _lastPostByUserUuid;
	private Date _lastPostDate;
	private double _priority;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserUuid;
	private String _statusByUserName;
	private Date _statusDate;
	private transient ExpandoBridge _expandoBridge;
}