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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.journal.configuration.JournalFileUploadsConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.TemplateContextHelper;

import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Eudaldo Alonso
 */
public class JournalEditDDMTemplateDisplayContext {

	public JournalEditDDMTemplateDisplayContext(HttpServletRequest request) {
		_request = request;

		_ddmTemplateHelper = (DDMTemplateHelper)_request.getAttribute(
			DDMTemplateHelper.class.getName());

		_journalFileUploadsConfiguration =
			(JournalFileUploadsConfiguration)_request.getAttribute(
				JournalFileUploadsConfiguration.class.getName());

		_journalWebConfiguration =
			(JournalWebConfiguration)_request.getAttribute(
				JournalWebConfiguration.class.getName());
	}

	public boolean autogenerateDDMTemplateKey() {
		return _journalWebConfiguration.autogenerateDDMTemplateKey();
	}

	public String getAutocompleteJSON() throws Exception {
		return _ddmTemplateHelper.getAutocompleteJSON(_request, getLanguage());
	}

	public long getClassPK() {
		if (_classPK != null) {
			return _classPK;
		}

		_classPK = BeanParamUtil.getLong(getDDMTemplate(), _request, "classPK");

		return _classPK;
	}

	public DDMStructure getDDMStructure() {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		DDMTemplate ddmTemplate = getDDMTemplate();

		_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			getClassPK());

		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		if (ddmTemplate != null) {
			_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
				ddmTemplate.getClassPK());
		}

		return _ddmStructure;
	}

	public DDMTemplate getDDMTemplate() {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		_ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
			getDDMTemplateId());

		return _ddmTemplate;
	}

	public long getDDMTemplateId() {
		if (_ddmTemplateId != null) {
			return _ddmTemplateId;
		}

		_ddmTemplateId = ParamUtil.getLong(_request, "ddmTemplateId");

		return _ddmTemplateId;
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_groupId = BeanParamUtil.getLong(
			getDDMTemplate(), _request, "groupId",
			themeDisplay.getScopeGroupId());

		return _groupId;
	}

	public String getLanguage() {
		if (_language != null) {
			return _language;
		}

		_language = BeanParamUtil.getString(
			getDDMTemplate(), _request, "language",
			TemplateConstants.LANG_TYPE_FTL);

		return _language;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_request, "redirect");

		return _redirect;
	}

	public String getScript() {
		if (_script != null) {
			return _script;
		}

		_language = BeanParamUtil.getString(
			getDDMTemplate(), _request, "language",
			TemplateConstants.LANG_TYPE_FTL);

		_script = BeanParamUtil.getString(getDDMTemplate(), _request, "script");

		if (Validator.isNull(_script)) {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(
					PortalUtil.getClassNameId(JournalArticle.class));

			_script = templateHandler.getTemplatesHelpContent(_language);
		}

		String scriptContent = ParamUtil.getString(_request, "scriptContent");

		if (Validator.isNotNull(scriptContent)) {
			_script = scriptContent;
		}

		return _script;
	}

	public ResourceBundle getTemplateHandlerResourceBundle() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				PortalUtil.getClassNameId(JournalArticle.class));

		Class<?> clazz = getClass();

		if (templateHandler != null) {
			clazz = templateHandler.getClass();
		}

		Bundle bundle = FrameworkUtil.getBundle(clazz);

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					bundle.getSymbolicName());

		return resourceBundleLoader.loadResourceBundle(
			themeDisplay.getLocale());
	}

	public Collection<TemplateVariableGroup> getTemplateVariableGroups()
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, TemplateVariableGroup> templateVariableGroups =
			TemplateContextHelper.getTemplateVariableGroups(
				PortalUtil.getClassNameId(JournalArticle.class), getClassPK(),
				getLanguage(), themeDisplay.getLocale());

		return templateVariableGroups.values();
	}

	public String getTitle() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMStructure ddmStructure = getDDMStructure();

		DDMTemplate ddmTemplate = getDDMTemplate();

		if ((ddmStructure != null) && (ddmTemplate != null)) {
			return StringUtil.appendParentheticalSuffix(
				ddmTemplate.getName(themeDisplay.getLocale()),
				ddmStructure.getName(themeDisplay.getLocale()));
		}

		if (ddmStructure != null) {
			return LanguageUtil.format(
				_request, "new-template-for-structure-x",
				ddmStructure.getName(themeDisplay.getLocale()), false);
		}

		if (ddmTemplate != null) {
			return ddmTemplate.getName(themeDisplay.getLocale());
		}

		return LanguageUtil.get(_request, "new-template");
	}

	public String[] imageExtensions() {
		return _journalFileUploadsConfiguration.imageExtensions();
	}

	public boolean isAutocompleteEnabled() {
		return _ddmTemplateHelper.isAutocompleteEnabled(getLanguage());
	}

	public boolean isCacheable() {
		if (_cacheable != null) {
			return _cacheable;
		}

		_cacheable = BeanParamUtil.getBoolean(
			getDDMTemplate(), _request, "cacheable", true);

		return _cacheable;
	}

	public boolean isShowCacheableInput() {
		if (_showCacheableInput != null) {
			return _showCacheableInput;
		}

		_showCacheableInput = ParamUtil.getBoolean(
			_request, "showCacheableInput");

		return _showCacheableInput;
	}

	public boolean isSmallImage() {
		if (_smallImage != null) {
			return _smallImage;
		}

		_smallImage = BeanParamUtil.getBoolean(
			getDDMTemplate(), _request, "smallImage", true);

		return _smallImage;
	}

	public long smallImageMaxSize() {
		return _journalFileUploadsConfiguration.smallImageMaxSize();
	}

	private Boolean _cacheable;
	private Long _classPK;
	private DDMStructure _ddmStructure;
	private DDMTemplate _ddmTemplate;
	private final DDMTemplateHelper _ddmTemplateHelper;
	private Long _ddmTemplateId;
	private Long _groupId;
	private final JournalFileUploadsConfiguration
		_journalFileUploadsConfiguration;
	private final JournalWebConfiguration _journalWebConfiguration;
	private String _language;
	private String _redirect;
	private final HttpServletRequest _request;
	private String _script;
	private Boolean _showCacheableInput;
	private Boolean _smallImage;

}