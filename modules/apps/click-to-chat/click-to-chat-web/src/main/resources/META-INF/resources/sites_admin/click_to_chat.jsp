<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
ClickToChatConfiguration clickToChatConfiguration = (ClickToChatConfiguration)request.getAttribute(ClickToChatConfiguration.class.getName());

boolean disabled = false;

if (Objects.equals(clickToChatConfiguration.siteSettingsStrategy(), "always-inherit") || Validator.isNull(clickToChatConfiguration.siteSettingsStrategy())) {
	disabled = true;
}

boolean clickToChatEnabled = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_ENABLED));
%>

<div class="form-group row">
	<div class="col-md-12">
		<label class="control-label">
			<liferay-ui:message key="site-settings-strategy" />

			<liferay-ui:icon-help message="site-settings-strategy-description" />
		</label>
	</div>

	<c:if test="<%= Validator.isNotNull(clickToChatConfiguration.siteSettingsStrategy()) %>">
		<div class="col-md-12">
			<liferay-ui:message key='<%= "site-settings-strategy-" + clickToChatConfiguration.siteSettingsStrategy() %>' />
		</div>
	</c:if>
</div>

<div class="row">
	<div class="col-md-12">
		<aui:input checked="<%= clickToChatEnabled %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enable-click-to-chat") %>' labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--clickToChatEnabled--" onchange="onChangeEnableClickToChat(event)" type="toggle-switch" value="<%= clickToChatEnabled %>" />
	</div>
</div>

<%
String clickToChatProviderId = GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ID));
%>

<div class="row">
	<div class="col-md-6">
		<aui:select label="chat-provider" name="TypeSettingsProperties--clickToChatProviderId--" onchange="onChangeProvider(event)" value="<%= clickToChatProviderId %>">
			<aui:option label="" value="" />

			<%
			for (String curClickToChatProviderId : ClickToChatConstants.CLICK_TO_CHAT_PROVIDER_IDS) {
			%>

				<aui:option label='<%= "chat-provider-" + curClickToChatProviderId %>' value="<%= curClickToChatProviderId %>" />

			<%
			}
			%>

		</aui:select>

		<%
		boolean clickToChatGuestUsersAllowed = GetterUtil.getBoolean(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_GUEST_USERS_ALLOWED));
		%>

		<aui:input checked="<%= clickToChatGuestUsersAllowed %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "guest-users-allowed") %>' labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--clickToChatGuestUsersAllowed--" type="toggle-switch" value="<%= clickToChatGuestUsersAllowed %>" />
	</div>

	<div class="col-md-6">
		<aui:input label="chat-provider-account-id" name="TypeSettingsProperties--clickToChatProviderAccountId--" type="text" value="<%= GetterUtil.getString(request.getAttribute(ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ACCOUNT_ID)) %>" />

			<% for (String curClickToChatProviderId :
			ClickToChatConstants.CLICK_TO_CHAT_PROVIDER_IDS) { %>

			<div class="<%= "mb-2 hide chat-provider-link chat-provider-link-to-" + curClickToChatProviderId %>">
				<liferay-learn:message
					key='<%= "chat-provider-account-id-help-" + curClickToChatProviderId %>'
					resource="click-to-chat-web"
				/>
			</div>

			<%
			}
			%>

	</div>
</div>

<script>
	var clickToChatEnabled = document.getElementById(
		'<portlet:namespace />clickToChatEnabled'
	);

	var clickToChatProviderId = document.getElementById(
		'<portlet:namespace />clickToChatProviderId'
	);

	var chatProviderAccountId = document.getElementById(
		'<portlet:namespace />clickToChatProviderAccountId'
	);

	var clickToChatGuestUsersAllowed = document.getElementById(
		'<portlet:namespace />clickToChatGuestUsersAllowed'
	);

	var disabled = JSON.parse('<%= disabled %>');

	if (disabled || !clickToChatEnabled.checked) {
		Liferay.Util.toggleDisabled(clickToChatProviderId, true);
		Liferay.Util.toggleDisabled(chatProviderAccountId, true);
		Liferay.Util.toggleDisabled(clickToChatGuestUsersAllowed, true);
	}

	function hideContainers() {
		const providers = document.querySelectorAll('.chat-provider-link');

		providers.forEach((provider) => {
			provider.classList.add('hide');
		});
	}

	function onChangeEnableClickToChat() {
		Liferay.Util.toggleDisabled(
			chatProviderAccountId,
			!clickToChatEnabled.checked
		);
		Liferay.Util.toggleDisabled(
			clickToChatGuestUsersAllowed,
			!clickToChatEnabled.checked
		);
		Liferay.Util.toggleDisabled(
			clickToChatProviderId,
			!clickToChatEnabled.checked
		);
	}

	function onChangeProvider(event) {
		hideContainers();

		showContainer(event.target.value);
	}

	function showContainer(name) {
		const provider = document.querySelector('.chat-provider-link-to-' + name);

		if (provider) {
			provider.classList.remove('hide');
		}
	}

	var currentProviderId = '<%=clickToChatProviderId%>';

	showContainer(currentProviderId);
</script>