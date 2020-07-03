package io.finbook.util;

public class Path {

	public static class HomeRoutes {
		public static final String INDEX = "/";
		public static final String ERROR_404 = "/*";
	}

	// Authentication routes
	public static class AuthRoutes {
		// Section
		public static final String AUTH = "/auth";

		// Routes
		public static final String LOGIN = "/login";
		public static final String SIGN = "/sign";
		public static final String LOGOUT = "/logout";
	}

	public static class AdminRoutes {
		// Section
		public static final String ADMIN = "/admin";

		// Filters
		public static final String ADMIN_FILTER = "/*";

		// ROUTES
		public static final String DASHBOARD = "/dashboard";

		// Products routes
		public static final String PRODUCTS = "/products";
		public static final String PRODUCTS_EMPTY = "";

		// Invoices routes
		public static final String INVOICES = "/invoices";
		public static final String INVOICES_EMPTY = "";

		// Reporting routes
		public static final String REPORTING = "/reporting";
		public static final String REPORTING_EMPTY = "";
		public static final String REPORTING_AJAX_DATEPICKER = "/ajax-datepicker";

	}

	public static class Template {
		public static final String HOME_INDEX = "home/index";
		public static final String HOME_LOGIN_INDEX = "home/login/index";
		public static final String HOME_LOGIN_SIGN = "home/login/sign";
		public static final String HOME_NOT_FOUND = "home/errors/404";
		public static final String HOME_INTERNAL_SERVER_ERROR = "home/errors/500";

		public static final String ADMIN_INDEX = "admin/index";
		public static final String ADMIN_PRODUCTS_LIST = "admin/products/list";
		public static final String ADMIN_INVOICES_LIST = "admin/invoices/list";
		public static final String ADMIN_REPORTING_INDEX = "admin/reporting/index";
	}


}
