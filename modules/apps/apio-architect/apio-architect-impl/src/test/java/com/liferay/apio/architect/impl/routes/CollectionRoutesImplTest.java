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

package com.liferay.apio.architect.impl.routes;

import static com.liferay.apio.architect.impl.routes.RoutesTestUtil.FORM_BUILDER_FUNCTION;
import static com.liferay.apio.architect.impl.routes.RoutesTestUtil.HAS_ADDING_PERMISSION_FUNCTION;
import static com.liferay.apio.architect.impl.routes.RoutesTestUtil.PAGINATION;
import static com.liferay.apio.architect.impl.routes.RoutesTestUtil.REQUEST_PROVIDE_FUNCTION;
import static com.liferay.apio.architect.impl.routes.RoutesTestUtil.keyValueFrom;
import static com.liferay.apio.architect.operation.HTTPMethod.POST;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.alias.routes.CreateItemFunction;
import com.liferay.apio.architect.alias.routes.GetPageFunction;
import com.liferay.apio.architect.form.Body;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.impl.routes.CollectionRoutesImpl.BuilderImpl;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.CollectionRoutes.Builder;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class CollectionRoutesImplTest {

	@Test
	public void testEmptyBuilderBuildsEmptyRoutes() {
		Builder<String, Long> builder = new BuilderImpl<>(
			"name", REQUEST_PROVIDE_FUNCTION,
			__ -> {
			},
			__ -> null);

		CollectionRoutes<String, Long> collectionRoutes = builder.build();

		Optional<CreateItemFunction<String>> createItemFunctionOptional =
			collectionRoutes.getCreateItemFunctionOptional();

		assertThat(createItemFunctionOptional, is(emptyOptional()));

		Optional<GetPageFunction<String>> getPageFunctionOptional =
			collectionRoutes.getGetPageFunctionOptional();

		assertThat(getPageFunctionOptional, is(emptyOptional()));
	}

	@Test
	public void testFiveParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new BuilderImpl<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add, __ -> null);

		CollectionRoutes<String, Long> collectionRoutes = builder.addCreator(
			this::_testAndReturnFourParameterCreatorRoute, String.class,
			Long.class, Boolean.class, Integer.class,
			HAS_ADDING_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnFourParameterGetterRoute, String.class,
			Long.class, Boolean.class, Integer.class
		).build();

		assertThat(
			neededProviders,
			contains(
				Boolean.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()));

		_testCollectionRoutes(collectionRoutes);
	}

	@Test
	public void testFourParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new BuilderImpl<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add, __ -> null);

		CollectionRoutes<String, Long> collectionRoutes = builder.addCreator(
			this::_testAndReturnThreeParameterCreatorRoute, String.class,
			Long.class, Boolean.class, HAS_ADDING_PERMISSION_FUNCTION,
			FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnThreeParameterGetterRoute, String.class,
			Long.class, Boolean.class
		).build();

		assertThat(
			neededProviders,
			contains(
				Boolean.class.getName(), Long.class.getName(),
				String.class.getName()));

		_testCollectionRoutes(collectionRoutes);
	}

	@Test
	public void testOneParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new BuilderImpl<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add, __ -> null);

		CollectionRoutes<String, Long> collectionRoutes = builder.addCreator(
			this::_testAndReturnNoParameterCreatorRoute,
			HAS_ADDING_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnNoParameterGetterRoute
		).build();

		assertThat(neededProviders.size(), is(0));

		_testCollectionRoutes(collectionRoutes);
	}

	@Test
	public void testThreeParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new BuilderImpl<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add, __ -> null);

		CollectionRoutes<String, Long> collectionRoutes = builder.addCreator(
			this::_testAndReturnTwoParameterCreatorRoute, String.class,
			Long.class, HAS_ADDING_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnTwoParameterGetterRoute, String.class,
			Long.class
		).build();

		assertThat(
			neededProviders,
			contains(Long.class.getName(), String.class.getName()));

		_testCollectionRoutes(collectionRoutes);
	}

	@Test
	public void testTwoParameterBuilderMethodsCreatesValidRoutes() {
		Set<String> neededProviders = new TreeSet<>();

		Builder<String, Long> builder = new BuilderImpl<>(
			"name", REQUEST_PROVIDE_FUNCTION, neededProviders::add, __ -> null);

		CollectionRoutes<String, Long> collectionRoutes = builder.addCreator(
			this::_testAndReturnOneParameterCreatorRoute, String.class,
			HAS_ADDING_PERMISSION_FUNCTION, FORM_BUILDER_FUNCTION
		).addGetter(
			this::_testAndReturnOneParameterGetterRoute, String.class
		).build();

		assertThat(neededProviders, contains(String.class.getName()));

		_testCollectionRoutes(collectionRoutes);
	}

	private String _testAndReturnFourParameterCreatorRoute(
		Map<String, Object> body, String string, Long aLong, Boolean aBoolean,
		Integer integer) {

		assertThat(integer, is(2017));

		return _testAndReturnThreeParameterCreatorRoute(
			body, string, aLong, aBoolean);
	}

	private PageItems<String> _testAndReturnFourParameterGetterRoute(
		Pagination pagination, String string, Long aLong, Boolean aBoolean,
		Integer integer) {

		assertThat(integer, is(2017));

		return _testAndReturnThreeParameterGetterRoute(
			pagination, string, aLong, aBoolean);
	}

	private String _testAndReturnNoParameterCreatorRoute(
		Map<String, Object> body) {

		assertThat(body.get("key"), is(keyValueFrom(_body)));

		return "Apio";
	}

	private PageItems<String> _testAndReturnNoParameterGetterRoute(
		Pagination pagination) {

		assertThat(pagination, is(PAGINATION));

		return new PageItems<>(Collections.singletonList("Apio"), 1);
	}

	private String _testAndReturnOneParameterCreatorRoute(
		Map<String, Object> body, String string) {

		assertThat(string, is("Apio"));

		return _testAndReturnNoParameterCreatorRoute(body);
	}

	private PageItems<String> _testAndReturnOneParameterGetterRoute(
		Pagination pagination, String string) {

		assertThat(string, is("Apio"));

		return _testAndReturnNoParameterGetterRoute(pagination);
	}

	private String _testAndReturnThreeParameterCreatorRoute(
		Map<String, Object> body, String string, Long aLong, Boolean aBoolean) {

		assertThat(aBoolean, is(true));

		return _testAndReturnTwoParameterCreatorRoute(body, string, aLong);
	}

	private PageItems<String> _testAndReturnThreeParameterGetterRoute(
		Pagination pagination, String string, Long aLong, Boolean aBoolean) {

		assertThat(aBoolean, is(true));

		return _testAndReturnTwoParameterGetterRoute(pagination, string, aLong);
	}

	private String _testAndReturnTwoParameterCreatorRoute(
		Map<String, Object> body, String string, Long aLong) {

		assertThat(aLong, is(42L));

		return _testAndReturnOneParameterCreatorRoute(body, string);
	}

	private PageItems<String> _testAndReturnTwoParameterGetterRoute(
		Pagination pagination, String string, Long aLong) {

		assertThat(aLong, is(42L));

		return _testAndReturnOneParameterGetterRoute(pagination, string);
	}

	private void _testCollectionRoutes(
		CollectionRoutes<String, Long> collectionRoutes) {

		_testCollectionRoutesCreator(collectionRoutes);

		_testCollectionRoutesGetter(collectionRoutes);
	}

	private void _testCollectionRoutesCreator(
		CollectionRoutes<String, Long> collectionRoutes) {

		Optional<Form> formOptional = collectionRoutes.getFormOptional();

		if (!formOptional.isPresent()) {
			throw new AssertionError("Create Form not present");
		}

		Form form = formOptional.get();

		assertThat(form.getId(), is("c/name"));

		Map map = (Map)form.get(_body);

		assertThat(map.get("key"), is(keyValueFrom(_body)));

		Optional<CreateItemFunction<String>> createItemFunctionOptional =
			collectionRoutes.getCreateItemFunctionOptional();

		if (!createItemFunctionOptional.isPresent()) {
			throw new AssertionError("CreateItemFunction not present");
		}

		CreateItemFunction<String> createItemFunction =
			createItemFunctionOptional.get();

		SingleModel<String> singleModel = createItemFunction.apply(
			null
		).andThen(
			Try::getUnchecked
		).apply(
			_body
		);

		assertThat(singleModel.getResourceName(), is("name"));
		assertThat(singleModel.getModel(), is("Apio"));
	}

	private void _testCollectionRoutesGetter(
		CollectionRoutes<String, Long> collectionRoutes) {

		Optional<GetPageFunction<String>> optional =
			collectionRoutes.getGetPageFunctionOptional();

		if (!optional.isPresent()) {
			throw new AssertionError("GetPageFunction not present");
		}

		GetPageFunction<String> getPageFunction = optional.get();

		Page<String> page = getPageFunction.andThen(
			Try::getUnchecked
		).apply(
			null
		);

		assertThat(page.getItems(), hasSize(1));
		assertThat(page.getItems(), hasItem("Apio"));
		assertThat(page.getTotalCount(), is(1));

		List<Operation> operations = page.getOperations();

		assertThat(operations, hasSize(1));

		Operation operation = operations.get(0);

		assertThat(operation.getFormOptional(), is(optionalWithValue()));
		assertThat(operation.getHttpMethod(), is(POST));
		assertThat(operation.getName(), is("name/create"));
	}

	private static final Body _body = __ -> Optional.of("Apio");

}