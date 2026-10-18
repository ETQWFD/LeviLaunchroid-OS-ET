package org.levimc.launcher.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import org.levimc.launcher.R;
import org.levimc.launcher.ui.animation.DynamicAnim;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AboutActivity extends BaseActivity {

    private static final String AVATAR_URL = "https://github.com/ETQWFD.png?v=4";
    private static final String URL_CONTACT = "mailto:2416444244@qq.com";
    private static final String URL_QQ_GROUP = "http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=UxrfqNnJERsNXBPZ3mjlQPCeGl3iti-d&authKey=u9Vs5w%2FC9tUFH4kpxuVx%2BatmXLeu8H05Ubjm%2BVABDH5z56dG%2Bj8hYa0ggctAwtcn&noverify=0&group_code=1104692081";
    private static final String URL_REPO = "https://github.com/ETQWFD/LeviLaunchroid-OS-ET";
    private static final String URL_ORG = "https://github.com/ETQWFD";
    private static final String URL_ISSUES = "https://github.com/ETQWFD/LeviLaunchroid-OS-ET/issues";
    private static final String MAINTAINER_AVATAR_URL = "https://github.com/ETQWFD.png?v=4";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupNavBar();

        loadAvatars();
        setupLinks();
        styleBadges();

        DynamicAnim.applyPressScaleRecursively(findViewById(android.R.id.content));
    }

    private void styleBadges() {
        TextView authorBadge = findViewById(R.id.author_badge);
        TextView maintainerBadge = findViewById(R.id.maintainer_badge);

        org.levimc.launcher.util.PersonalizationManager pm = new org.levimc.launcher.util.PersonalizationManager(this);
        int accent = pm.getAccentColor();
        if (accent != 0) {
            android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
            gd.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
            gd.setColor(accent);
            gd.setCornerRadius(4 * getResources().getDisplayMetrics().density);
            if (authorBadge != null) {
                authorBadge.setBackground(gd);
                authorBadge.setTextColor(android.graphics.Color.WHITE);
            }
            if (maintainerBadge != null) {
                maintainerBadge.setBackground(gd);
                maintainerBadge.setTextColor(android.graphics.Color.WHITE);
            }
        }
    }

    private void setupNavBar() {
        setActiveNavTab(R.id.nav_tab_about);
        findViewById(R.id.nav_tab_about).setOnClickListener(v -> {});
    }

    private void loadAvatars() {
        com.microsoft.xbox.idp.toolkit.CircleImageView avatar = findViewById(R.id.author_avatar);
        com.microsoft.xbox.idp.toolkit.CircleImageView maintainerAvatar = findViewById(R.id.maintainer_avatar);

        executor.execute(() -> {
            try {
                if (avatar != null) {
                    Response resp = client.newCall(new Request.Builder().url(AVATAR_URL).build()).execute();
                    if (resp.isSuccessful() && resp.body() != null) {
                        Bitmap bmp = BitmapFactory.decodeStream(resp.body().byteStream());
                        runOnUiThread(() -> {
                            if (bmp != null) avatar.setImageBitmap(bmp);
                        });
                    }
                }
            } catch (Exception ignored) {}

            try {
                if (maintainerAvatar != null) {
                    Response resp = client.newCall(new Request.Builder().url(MAINTAINER_AVATAR_URL).build()).execute();
                    if (resp.isSuccessful() && resp.body() != null) {
                        Bitmap bmp = BitmapFactory.decodeStream(resp.body().byteStream());
                        runOnUiThread(() -> {
                            if (bmp != null) maintainerAvatar.setImageBitmap(bmp);
                        });
                    }
                }
            } catch (Exception ignored) {}
        });
    }

    private void setupLinks() {
        setupLinkButton(R.id.btn_afdian, URL_CONTACT);
        setupLinkButton(R.id.btn_patreon, URL_QQ_GROUP);
        setupLinkButton(R.id.btn_github_repo, URL_REPO);
        setupLinkButton(R.id.btn_github_org, URL_ORG);
        setupLinkButton(R.id.btn_issues, URL_ISSUES);
        setupLinkButton(R.id.btn_star_fork, URL_REPO);
        setupLinkButton(R.id.btn_youtube_maintainer, URL_REPO);
    }

    private void setupLinkButton(int viewId, String url) {
        TextView btn = findViewById(viewId);
        if (btn == null) return;
        btn.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } catch (Exception ignored) {}
        });
        DynamicAnim.applyPressScale(btn);
    }
}
