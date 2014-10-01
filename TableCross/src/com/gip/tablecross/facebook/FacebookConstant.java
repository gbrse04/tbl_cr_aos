package com.gip.tablecross.facebook;

public class FacebookConstant {

	/*
	 * User and Friends Permissions
	 */
	public static final String FACEBOOK_PERMISSION_USER_ABOUT_ME = "user_about_me";
	public static final String FACEBOOK_PERMISSION_FRIENDS_ABOUT_ME = "friends_about_me";
	// Provides access to the "About Me" section of the profile in the about
	// property

	public static final String FACEBOOK_PERMISSION_USER_ACTIVITIES = "user_activities";
	public static final String FACEBOOK_PERMISSION_FRIENDS_ACTIVITIES = "friends_activities";
	// Provides access to the user's list of activities as the activities
	// connection

	public static final String FACEBOOK_PERMISSION_USER_BIRTHDAY = "user_birthday";
	public static final String FACEBOOK_PERMISSION_FRIENDS_BIRTHDAY = "friends_birthday";
	// Provides access to the birthday with year as the birthday property

	public static final String FACEBOOK_PERMISSION_USER_CHECKINS = "user_checkins";
	public static final String FACEBOOK_PERMISSION_FRIENDS_CHECKINS = "friends_checkins";
	// Provides read access to the authorized user's check-ins or a friend's
	// check-ins that the user can see. This permission is superseded by
	// user_status for new applications as of March, 2012.

	public static final String FACEBOOK_PERMISSION_USER_EDUCATION_HISTORY = "user_education_history";
	public static final String FACEBOOK_PERMISSION_FRIENDS_EDUCATION_HISTORY = "friends_education_history";
	// Provides access to education history as the education property

	public static final String FACEBOOK_PERMISSION_USER_EVENTS = "user_events";
	public static final String FACEBOOK_PERMISSION_FRIENDS_EVENTS = "friends_events";
	// Provides access to the list of events the user is attending as the events
	// connection

	public static final String FACEBOOK_PERMISSION_USER_GROUPS = "user_groups";
	public static final String FACEBOOK_PERMISSION_FRIENDS_GROPUS = "friends_groups";
	// Provides access to the list of groups the user is a member of as the
	// groups connection

	public static final String FACEBOOK_PERMISSION_USER_HOMETOWN = "user_hometown";
	public static final String FACEBOOK_PERMISSION_FRIENDS_HOMETOWN = "friends_hometown";
	// Provides access to the user's hometown in the hometown property

	public static final String FACEBOOK_PERMISSION_USER_INTERESTS = "user_interests";
	public static final String FACEBOOK_PERMISSION_FRIENDS_INTERESTS = "friends_interests";
	// Provides access to the user's list of interests as the interests
	// connection

	public static final String FACEBOOK_PERMISSION_USER_LIKES = "user_likes";
	public static final String FACEBOOK_PERMISSION_FRIENDS_LIKES = "friends_likes";
	// Provides access to the list of all of the pages the user has liked as the
	// likes connection

	public static final String FACEBOOK_PERMISSION_USER_LOCATION = "user_location";
	public static final String FACEBOOK_PERMISSION_FRIENDS_LOCATION = "friends_location";
	// Provides access to the user's current location as the location property

	public static final String FACEBOOK_PERMISSION_USER_NOTES = "user_notes";
	public static final String FACEBOOK_PERMISSION_FRIENDS_NOTES = "friends_notes";
	// Provides access to the user's notes as the notes connection

	public static final String FACEBOOK_PERMISSION_USER_PHOTOS = "user_photos";
	public static final String FACEBOOK_PERMISSION_FRIENDS_PHOTOS = "friends_photos";
	// Provides access to the photos the user has uploaded, and photos the user
	// has been tagged in

	public static final String FACEBOOK_PERMISSION_USER_QUESTION = "user_questions";
	public static final String FACEBOOK_PERMISSION_FRIENDS_QUESTION = "friends_questions";
	// Provides access to the questions the user or friend has asked

	public static final String FACEBOOK_PERMISSION_USER_RELATIONSHIPS = "user_relationships";
	public static final String FACEBOOK_PERMISSION_FRIENDS_RELATIONSHIPS = "friends_relationships";
	// Provides access to the user's family and personal relationships and
	// relationship status

	public static final String FACEBOOK_PERMISSION_USER_RELATIONSHIP_DETAILS = "user_relationship_details";
	public static final String FACEBOOK_PERMISSION_FRIENDS_RELATIONSHIP_DETAILS = "friends_relationship_details";
	// Provides access to the user's relationship preferences

	public static final String FACEBOOK_PERMISSION_USER_RELIGION_POLITICS = "user_religion_politics";
	public static final String FACEBOOK_PERMISSION_FRIENDS_RELIGION_POLITICS = "friends_religion_politics";
	// Provides access to the user's religious and political affiliations

	public static final String FACEBOOK_PERMISSION_USER_STATUS = "user_status";

	public static final String FACEBOOK_PERMISSION_FRIENDS_STATUS = "friends_status";
	// Provides access to the user's status messages and checkins. Please see
	// the documentation for the location_post table for information on how this
	// permission may affect retrieval of information about the locations
	// associated with posts.

	public static final String FACEBOOK_PERMISSION_USER_SUBSCRIPTIONS = "user_subscriptions";
	public static final String FACEBOOK_PERMISSION_FRIENDS_SUBSCRIPTIONS = "friends_subscriptions";
	// Provides access to the user's subscribers and subscribees

	public static final String FACEBOOK_PERMISSION_USER_VIDEOS = "user_videos";
	public static final String FACEBOOK_PERMISSION_FRIENDS_VIDEOS = "friends_videos";
	// Provides access to the videos the user has uploaded, and videos the user
	// has been tagged in

	public static final String FACEBOOK_PERMISSION_USER_WEBSITE = "user_website";
	public static final String FACEBOOK_PERMISSION_FRIENDS_WEBSITE = "friends_website";
	// Provides access to the user's web site URL

	public static final String FACEBOOK_PERMISSION_USER_WORK_HISTORY = "user_work_history";
	public static final String FACEBOOK_PERMISSION_FRIENDS_WORK_HISTORY = "friends_work_history";
	// Provides access to work history as the work property

	public static final String FACEBOOK_PERMISSION_USER_EMAIL = "email";
	public static final String FACEBOOK_PERMISSION_FRIENDS_EMAIL = "";
	// Provides access to the user's primary email address in the email
	// property. Do not spam users. Your use of email must comply both with
	// Facebook policies and with the CAN-SPAM Act.

	// We are now making a user’s address and mobile phone number accessible
	// as part of the User Graph object. Because this is sensitive information,
	// we have created the new user_address and user_mobile_phone permissions.
	// These permissions must be explicitly granted to your application by the
	// user via our standard permissions dialogs.

	// public static final String FACEBOOK_PERMISSON_USER_PHONE_NUMBER =
	// "user_mobile_phone";

	/*
	 * Extended Permissions
	 */
	public static final String FACEBOOK_PERMISSION_READ_FRIENDLISTS = "read_friendlists";
	// Provides access to any friend lists the user created. All user's friends
	// are provided as part of basic data, this extended permission grants
	// access to the lists of friends a user has created, and should only be
	// requested if your application utilizes lists of friends.

	public static final String FACEBOOK_PERMISSION_READINSIGHTS = "read_insights";
	// Provides read access to the Insights data for pages, applications, and
	// domains the user owns.

	public static final String FACEBOOK_PERMISSION_READ_MAILBOX = "read_mailbox";
	// Provides the ability to read from a user's Facebook Inbox.

	public static final String FACEBOOK_PERMISSION_READ_REQUESTS = "read_requests";
	// Provides read access to the user's friend requests

	public static final String FACEBOOK_PERMISSION_READ_STREAM = "read_stream";
	// Provides access to all the posts in the user's News Feed and enables your
	// application to perform searches against the user's News Feed

	public static final String FACEBOOK_PERMISSION_XMPP_LOGIN = "xmpp_login";
	// Provides applications that integrate with Facebook Chat the ability to
	// log in users.

	public static final String FACEBOOK_PERMISSION_ADS_MANAGEMENT = "ads_management";
	// Provides the ability to manage ads and call the Facebook Ads API on
	// behalf of a user.

	public static final String FACEBOOK_PERMISSION_CREATE_EVENT = "create_event";
	// Enables your application to create and modify events on the user's behalf

	public static final String FACEBOOK_PERMISSION_MANAGE_FRIENDLISTS = "manage_friendlists";
	// Enables your app to create and edit the user's friend lists.

	public static final String FACEBOOK_PERMISSION_MANAGE_NOTIFICATIONS = "manage_notifications";
	// Enables your app to read notifications and mark them as read. Intended
	// usage: This permission should be used to let users read and act on their
	// notifications; it should not be used to for the purposes of modeling user
	// behavior or data mining. Apps that misuse this permission may be banned
	// from requesting it.

	public static final String FACEBOOK_PERMISSION_USER_ONLINE_PRESENCE = "user_online_presence";
	// Provides access to the user's online/offline presence

	public static final String FACEBOOK_PERMISSION_FRIENDS_ONLINE_PRESENCE = "friends_online_presence";
	// Provides access to the user's friend's online/offline presence

	public static final String FACEBOOK_PERMISSION_PUBLISH_CHECKINS = "publish_checkins";
	// Enables your app to perform checkins on behalf of the user.

	public static final String FACEBOOK_PERMISSION_PUBLISH_STREAM = "publish_stream";
	// Enables your app to post content, comments, and likes to a user's stream
	// and to the streams of the user's friends. This is a superset publishing
	// permission which also includes publish_actions. However, please note that
	// Facebook recommends a user-initiated sharing model. Please read the
	// Platform Policies to ensure you understand how to properly use this
	// permission. Note, you do not need to request the publish_stream
	// permission in order to use the Feed Dialog, the Requests Dialog or the
	// Send Dialog.

	public static final String FACEBOOK_PERMISSION_RSVP_EVENT = "rsvp_event";
	// Enables your application to RSVP to events on the user's behalf

	public static final String FACEBOOK_APPID = "604908959562677";
	public static final String[] FACEBOOK_PERMISSION = new String[] { FACEBOOK_PERMISSION_PUBLISH_STREAM,
			FACEBOOK_PERMISSION_USER_EMAIL };
}
