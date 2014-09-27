/*
 * Name: $RCSfile: ParameterFactory.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 2:45:36 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.gip.tablecross.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * ParameterFactory class builds parameters for web service apis
 * 
 */
public final class ParameterFactory {
	
	public static List<NameValuePair> createGetFacebookUserInfoParam(String accessToken) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("access_token", accessToken));

		return parameters;

	}
	public static List<NameValuePair> createAddCustomerParams(String sessionId,
			int idType, int idTitle, String dateBirth, String firstName,
			String lastName, String company, String phoneNumber, String email,
			String emailConfirm, String password, String passwordConfirm,
			String emailSponsor, int idOrigin, String originOther) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters
				.add(new BasicNameValuePair("id_type", String.valueOf(idType)));
		parameters.add(new BasicNameValuePair("id_title", String
				.valueOf(idTitle)));
		parameters.add(new BasicNameValuePair("date_birth", dateBirth));
		parameters.add(new BasicNameValuePair("firstname", firstName));
		parameters.add(new BasicNameValuePair("lastname", lastName));
		parameters.add(new BasicNameValuePair("company", company));
		parameters.add(new BasicNameValuePair("phonenumber", phoneNumber));
		parameters.add(new BasicNameValuePair("email", email));
		parameters.add(new BasicNameValuePair("email_confirm", emailConfirm));
		parameters.add(new BasicNameValuePair("password", password));
		parameters.add(new BasicNameValuePair("password_confirm",
				passwordConfirm));
		parameters.add(new BasicNameValuePair("email_sponsor", emailSponsor));
		parameters.add(new BasicNameValuePair("id_origin", String
				.valueOf(idOrigin)));
		parameters.add(new BasicNameValuePair("origin_other", originOther));
		return parameters;
	}

	public static List<NameValuePair> createLoginParams(String sessionId,
			String email, String password) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("email", email));
		parameters.add(new BasicNameValuePair("password", password));
		return parameters;
	}

	public static List<NameValuePair> createRetrievePasswordParams(
			String sessionId, String email) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("email", email));
		return parameters;
	}

	public static List<NameValuePair> createEditCustomerParams(
			String sessionId, int idTitle, String dateBirth, String firstname,
			String lastname, String company, String phonenumber, String email) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("id_title", String
				.valueOf(idTitle)));
		parameters.add(new BasicNameValuePair("date_birth", dateBirth));
		parameters.add(new BasicNameValuePair("firstname", firstname));
		parameters.add(new BasicNameValuePair("lastname", lastname));
		parameters.add(new BasicNameValuePair("company", company));
		parameters.add(new BasicNameValuePair("phonenumber", phonenumber));
		parameters.add(new BasicNameValuePair("email", email));
		return parameters;
	}

	public static List<NameValuePair> createEditCustomerPasswordParams(
			String sessionId, String passwordOld, String password,
			String passwordConfirm) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("password_old", passwordOld));
		parameters.add(new BasicNameValuePair("password", password));
		parameters.add(new BasicNameValuePair("password_confirm",
				passwordConfirm));
		return parameters;
	}

	public static List<NameValuePair> createPostGeoCitiesParams(
			String sessionId, String zipcode) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("zipcode", zipcode));
		return parameters;
	}

	public static List<NameValuePair> createGeoAddressAutocompleteParams(
			String sessionId, String zipcode, String city, String streetName) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("zipcode", zipcode));
		parameters.add(new BasicNameValuePair("city", city));
		parameters.add(new BasicNameValuePair("street_name", streetName));
		return parameters;
	}

	public static List<NameValuePair> createGeoAddressParams(String sessionId,
			String zipcode, String city, String streetNumber, String streetName) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("zipcode", zipcode));
		parameters.add(new BasicNameValuePair("city", city));
		parameters.add(new BasicNameValuePair("street_number", streetNumber));
		parameters.add(new BasicNameValuePair("street_name", streetName));
		return parameters;
	}

	public static List<NameValuePair> createCustomerAddressParams(
			String sessionId, String idCustomerAddress, String name,
			String building, String code1, String intercom, String floor) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("id_customer_address",
				idCustomerAddress));
		parameters.add(new BasicNameValuePair("name", name));
		parameters.add(new BasicNameValuePair("building", building));
		parameters.add(new BasicNameValuePair("code1", code1));
		parameters.add(new BasicNameValuePair("intercom", intercom));
		parameters.add(new BasicNameValuePair("floor", floor));
		return parameters;
	}

	public static List<NameValuePair> createDeleteCustomerAddressParams(
			String sessionId, String idCustomerAddress) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("id_customer_address",
				idCustomerAddress));
		return parameters;
	}

	public static List<NameValuePair> createSponsorshipAddParams(
			String sessionId, String emails) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("emails", emails));
		return parameters;
	}

	public static List<NameValuePair> createOrderItemAddParams(
			String sessionId, String idItem, String qty, String idPromotion) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("id_item", idItem));
		parameters.add(new BasicNameValuePair("qty", qty));
		parameters.add(new BasicNameValuePair("id_promotion", idPromotion));
		return parameters;
	}

	public static List<NameValuePair> createOrderItemDeleteParams(
			String sessionId, String idOrderItem) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("id_order_item", idOrderItem));
		return parameters;
	}

	public static List<NameValuePair> createOrderItemQuantityParams(
			String sessionId, String idOrderItem, String qty) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("id_order_item", idOrderItem));
		parameters.add(new BasicNameValuePair("qty", qty));
		return parameters;
	}

	public static List<NameValuePair> createOrderItemCutleryParams(
			String sessionId, String qty) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("qty", qty));
		return parameters;
	}

	public static List<NameValuePair> createOrderCodeParams(String sessionId,
			String code) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("code", code));
		return parameters;
	}

	public static List<NameValuePair> createOrderPointsUsedParams(
			String sessionId, String points) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("points", points));
		return parameters;
	}

	public static List<NameValuePair> createOrderActionParams(String sessionId,
			String idDeliveryType, String idMember, String idCustomerAddress,
			String deliveryDate, String deliveryTime, String asap) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("id_delivery_type",
				idDeliveryType));
		parameters.add(new BasicNameValuePair("id_member", idMember));
		parameters.add(new BasicNameValuePair("id_customer_address",
				idCustomerAddress));
		parameters.add(new BasicNameValuePair("delivery_date", deliveryDate));
		parameters.add(new BasicNameValuePair("delivery_time", deliveryTime));
		parameters.add(new BasicNameValuePair("asap", asap));
		return parameters;
	}

	public static List<NameValuePair> createOrderPaymentParams(
			String sessionId, String idPaymentType) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters
				.add(new BasicNameValuePair("id_payment_type", idPaymentType));
		return parameters;
	}

	public static List<NameValuePair> createAddFavouriteParams(
			String sessionId, String foodId) {

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("session_id", sessionId));
		parameters.add(new BasicNameValuePair("id_item", foodId));
		return parameters;

	}
}
