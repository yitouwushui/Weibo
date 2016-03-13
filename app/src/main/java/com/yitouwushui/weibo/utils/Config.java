package com.yitouwushui.weibo.utils;

import android.os.Environment;

public class Config {

	public static final String MUSICS[] = { "mp3", "ogg", "mid", "asf", "wav",
			"wma", "ape", "vqf", "midi", "flac", "aac", "amr" };
	public static final String VIDEOS[] = { "mp4", "rmvb", "rm", "avi", "3gp",
			"mkv", "mpg", "vob", "mov", "flv", "swf" };
	public static final String PICTURES[] = { "jpg", "jpeg", "ico", "bmp",
			"gif", "tif", "pcx", "tga", "png" };
	public static final String OFFICES[] = { "doc", "docx", "dot", "dotx",
			"docm", "dotm", "pdf", "xls", "xlsx", "xlsm", "ppt", "pptm",
			"pptx", "pps", "ppsm", "ppa", "ppam", "pot", "potm", "potx" };

	public static String path1 = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
	public static String path2 = "/";

	public final static String[][] MIME_MapTable = {
			// {后缀名，MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "video/rmvb" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };

	public static boolean hiddenDot = false;// 隐藏以点开头的吗？
	public static boolean hiddenNotDisplay = false;// 不显示隐藏文件吗？

	public static final int DIALOG_ATTRIBUTE = 0;
	public static final int DIALOG_RENAME = 1;
	public static final int DIALOG_DELETE = 2;
	public static final int DIALOG_CREATE_FILE = 3;
	public static final int DIALOG_CREATE_FLODER = 4;
	public static final int DIALOG_PROGRESS = 5;
	public static final int DIALOG_UP = 6;
}
