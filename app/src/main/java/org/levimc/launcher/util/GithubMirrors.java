package org.levimc.launcher.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ET 版：GitHub 资源（release 附件、raw 文件等）在部分网络下直连会超时。
 * 这里按“直连优先，失败后依次尝试公共镜像”的顺序生成候选地址。
 * 镜像仅在前面的地址失败时使用，不改变原始文件内容（下载后仍做校验）。
 */
public final class GithubMirrors {

    private GithubMirrors() {}

    // 公共 GitHub 加速前缀，会随时间失效，多个一起尝试以提高成功率。
    private static final String[] PREFIXES = {
            "https://ghfast.top/",
            "https://gh-proxy.com/",
            "https://ghproxy.net/",
            "https://mirror.ghproxy.com/",
            "https://gh.llkk.cc/",
            "https://github.moeyy.xyz/",
            "https://ghproxy.homeboyc.cn/"
    };

    public static boolean isGithubHosted(String url) {
        if (url == null) return false;
        String lower = url.toLowerCase();
        return lower.startsWith("https://github.com/")
                || lower.startsWith("http://github.com/")
                || lower.startsWith("https://raw.githubusercontent.com/")
                || lower.startsWith("http://raw.githubusercontent.com/")
                || lower.contains(".githubusercontent.com/")
                || lower.startsWith("https://codeload.github.com/");
    }

    /**
     * 返回下载候选地址：原始地址在前，公共镜像在后。
     */
    public static List<String> candidates(String url) {
        List<String> list = new ArrayList<>();
        if (url == null || url.isEmpty()) return list;
        list.add(url);
        if (isGithubHosted(url)) {
            String normalized = url;
            if (normalized.startsWith("http://")) {
                normalized = "https://" + normalized.substring("http://".length());
            }
            for (String prefix : PREFIXES) {
                list.add(prefix + normalized);
            }
        }
        return list;
    }
}
