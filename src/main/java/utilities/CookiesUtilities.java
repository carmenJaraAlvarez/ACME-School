
package utilities;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class CookiesUtilities {

	private CookiesUtilities() {
	}

	public static Cookie getCookie(final HttpServletRequest request, final String name) {
		if (request.getCookies() != null)
			for (final Cookie cookie : request.getCookies())
				if (cookie.getName().equals(name))
					return cookie;

		return null;
	}
}
