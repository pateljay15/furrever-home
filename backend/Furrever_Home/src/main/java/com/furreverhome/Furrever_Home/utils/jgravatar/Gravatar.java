package com.furreverhome.Furrever_Home.utils.jgravatar;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * A gravatar is a dynamic image resource that is requested from the
 * gravatar.com server. This class calculates the gravatar url and fetches
 * gravatar images. See http://en.gravatar.com/site/implement/url .
 * 
 * This class is thread-safe, Gravatar objects can be shared.
 * 
 * Usage example:
 * 
 * <code>
 * Gravatar gravatar = new Gravatar();
 * gravatar.setSize(50);
 * gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
 * gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
 * String url = gravatar.getUrl("iHaveAn@email.com");
 * byte[] jpg = gravatar.download("info@ralfebert.de");
 * </code>
 */
public final class Gravatar {

	private final static int DEFAULT_SIZE = 80;
	private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";
	private static final GravatarRating DEFAULT_RATING = GravatarRating.GENERAL_AUDIENCES;
	private static final GravatarDefaultImage DEFAULT_DEFAULT_IMAGE = GravatarDefaultImage.HTTP_404;

	private int size = DEFAULT_SIZE;
	private GravatarRating rating = DEFAULT_RATING;
	private GravatarDefaultImage defaultImage = DEFAULT_DEFAULT_IMAGE;

	/**
	 * Specify a gravatar size between 1 and 512 pixels. If you omit this, a
	 * default size of 80 pixels is used.
	 */
	public void setSize(int sizeInPixels) {
		int maximunImageSize = 512;
		Validate.isTrue(sizeInPixels >= 1 && sizeInPixels <= maximunImageSize,
				"sizeInPixels needs to be between 1 and 512");
		this.size = sizeInPixels;
	}

	/**
	 * Specify a rating to ban gravatar images with explicit content.
	 */
	public void setRating(GravatarRating rating) {
		Validate.notNull(rating, "rating");
		this.rating = rating;
	}

	/**
	 * Specify the default image to be produced if no gravatar image was found.
	 */
	public void setDefaultImage(GravatarDefaultImage defaultImage) {
		Validate.notNull(defaultImage, "defaultImage");
		this.defaultImage = defaultImage;
	}

	/**
	 * Returns the Gravatar URL for the given email address.
	 */
	public String getUrl(String email) {
		Validate.notNull(email, "email");

		// hexadecimal MD5 hash of the requested user's lowercased email address
		// with all whitespace trimmed
		String emailHash = DigestUtils.md5Hex(email.toLowerCase().trim());
		String params = formatUrlParameters();
		return GRAVATAR_URL + emailHash + ".jpg" + params;
	}

	private String formatUrlParameters() {
		List<String> params = new ArrayList<String>();

		if (size != DEFAULT_SIZE)
			params.add("s=" + size);
		if (rating != DEFAULT_RATING)
			params.add("r=" + rating.getCode());
		if (defaultImage != GravatarDefaultImage.GRAVATAR_ICON)
			params.add("d=" + defaultImage.getCode());

		if (params.isEmpty())
			return "";
		else
			return "?" + StringUtils.join(params.iterator(), "&");
	}

}
