package io.finbook.util;

public class Path {

	public static class HomeRoutes {
		public static final String INDEX = "/";
	}

	// Authentication routes
	public static class AuthRoutes {
		// Section
		public static final String AUTH = "/auth";

		// Routes
		public static final String SIGN_IN = "/sign-in";
		public static final String SIGN_CERTIFICATE = "/sign-certificate";
		public static final String SIGN_OUT = "/sign-out";
		public static final String GET_CURRENT_USER_ID = "/get-current-user-id";
	}

	public static class AdminRoutes {
		// Section
		public static final String ADMIN = "/admin";

		// Filters
		public static final String ADMIN_FILTER = "/*";

		// ROUTES
		public static final String DASHBOARD = "/dashboard";

		// Invoices routes
		public static final String INVOICES = "/invoices";
		public static final String INVOICES_EMPTY = "";

		// Reporting routes
		public static final String REPORTING = "/reporting";
		public static final String VAT_RETURNS = "/vat-returns";
		public static final String REPORTING_AJAX_DATEPICKER = "/ajax-datepicker";
		public static final String REPORTING_AJAX_SEND_REPORT = "/ajax-send-report";

	}

	public static class Template {
		public static final String HOME_INDEX = "home/index";
		public static final String HOME_LOGIN_INDEX = "home/signin/index";
		public static final String HOME_LOGIN_SIGN = "home/signin/sign";
		public static final String HOME_NOT_FOUND = "home/errors/404";
		public static final String HOME_INTERNAL_SERVER_ERROR = "home/errors/500";

		public static final String ADMIN_INDEX = "admin/index";
		public static final String ADMIN_INVOICES_LIST = "admin/invoices/list";
		public static final String ADMIN_VAT_RETURNS_INDEX = "admin/reporting/vat-returns";
	}


}
