/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.asset.model.adapter.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetLinkWrapper;
import com.liferay.asset.kernel.model.adapter.StagedAssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class StagedAssetLinkImpl
	extends AssetLinkWrapper implements StagedAssetLink {

	public StagedAssetLinkImpl(AssetLink assetLink) {
		super(assetLink);

		populateEntry1Attributes();
		populateEntry2Attributes();

		populateUuid();
	}

	@Override
	public String getEntry1ClassName() {
		if (Validator.isNotNull(_entry1ClassName)) {
			return _entry1ClassName;
		}

		populateEntry1Attributes();

		return _entry1ClassName;
	}

	@Override
	public String getEntry1Uuid() {
		if (Validator.isNotNull(_entry1Uuid)) {
			return _entry1Uuid;
		}

		populateEntry1Attributes();

		return _entry1Uuid;
	}

	@Override
	public String getEntry2ClassName() {
		if (Validator.isNotNull(_entry2ClassName)) {
			return _entry2ClassName;
		}

		populateEntry2Attributes();

		return _entry2ClassName;
	}

	@Override
	public String getEntry2Uuid() {
		if (Validator.isNotNull(_entry2Uuid)) {
			return _entry2Uuid;
		}

		populateEntry2Attributes();

		return _entry2Uuid;
	}

	@Override
	public Date getModifiedDate() {
		return getCreateDate();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedAssetLink.class);
	}

	@Override
	public String getUuid() {
		if (Validator.isNotNull(_uuid)) {
			return _uuid;
		}

		populateUuid();

		return _uuid;
	}

	@Override
	public void persist() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCompanyId(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCreateDate(Date createDate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEntryId1(long entryId1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEntryId2(long entryId2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLinkId(long linkId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModifiedDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNew(boolean n) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setType(int type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserId(long userId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserName(String userName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserUuid(String userUuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWeight(int weight) {
		throw new UnsupportedOperationException();
	}

	protected void populateEntry1Attributes() {
		if (Validator.isNotNull(_entry1ClassName) &&
			Validator.isNotNull(_entry1Uuid)) {

			return;
		}

		AssetEntry entry1 = AssetEntryLocalServiceUtil.fetchAssetEntry(
			getEntryId1());

		if (entry1 == null) {
			return;
		}

		_entry1ClassName = entry1.getClassName();
		_entry1Uuid = entry1.getClassUuid();
	}

	protected void populateEntry2Attributes() {
		if (Validator.isNotNull(_entry2ClassName) &&
			Validator.isNotNull(_entry2Uuid)) {

			return;
		}

		AssetEntry entry2 = AssetEntryLocalServiceUtil.fetchAssetEntry(
			getEntryId2());

		if (entry2 == null) {
			return;
		}

		_entry2ClassName = entry2.getClassName();
		_entry2Uuid = entry2.getClassUuid();
	}

	protected void populateUuid() {
		if (Validator.isNotNull(_uuid)) {
			return;
		}

		String entry1Uuid = getEntry1Uuid();
		String entry2Uuid = getEntry2Uuid();

		if (Validator.isNotNull(entry1Uuid) &&
			Validator.isNotNull(entry2Uuid)) {

			_uuid = entry1Uuid + StringPool.POUND + entry2Uuid;
		}
	}

	@Override
	protected AssetLinkWrapper wrap(AssetLink assetLink) {
		return new StagedAssetLinkImpl(assetLink);
	}

	private String _entry1ClassName;
	private String _entry1Uuid;
	private String _entry2ClassName;
	private String _entry2Uuid;
	private String _uuid;

}