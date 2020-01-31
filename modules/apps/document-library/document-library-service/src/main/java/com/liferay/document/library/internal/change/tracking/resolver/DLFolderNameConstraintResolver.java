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

package com.liferay.document.library.internal.change.tracking.resolver;

import com.liferay.change.tracking.resolver.ConstraintResolver;
import com.liferay.change.tracking.resolver.helper.ConstraintResolverHelper;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Trong Tran
 */
@Component(service = ConstraintResolver.class)
public class DLFolderNameConstraintResolver
	implements ConstraintResolver<DLFolder> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-folder-name";
	}

	@Override
	public Class<DLFolder> getModelClass() {
		return DLFolder.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "rename-the-folder-in-the-publication";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, "com.liferay.document.library.service");
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "parentFolderId", "name"};
	}

	@Override
	public void resolveConflict(
		ConstraintResolverHelper<DLFolder> constraintResolverHelper) {
	}

}