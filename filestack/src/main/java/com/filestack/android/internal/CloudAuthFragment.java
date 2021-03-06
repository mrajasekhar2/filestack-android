package com.filestack.android.internal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.filestack.android.FsActivity;
import com.filestack.android.R;

/** Displays "cloud auth required" message and creates an intent to open the link in the browser. */
public class CloudAuthFragment extends Fragment implements
        View.OnClickListener, BackButtonListener {
    private final static String ARG_SOURCE = "source";
    private final static String ARG_AUTH_URL = "authUrl";

    private final static int[] TEXT_VIEW_IDS = {
            R.id.title, R.id.description, R.id.button, R.id.action };

    private SourceInfo sourceInfo;
    private String authUrl;

    public static CloudAuthFragment create(String source, String authUrl) {
        CloudAuthFragment fragment = new CloudAuthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SOURCE, source);
        args.putString(ARG_AUTH_URL, authUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        sourceInfo = Util.getSourceInfo(args.getString(ARG_SOURCE));
        authUrl = args.getString(ARG_AUTH_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View baseView = inflater.inflate(R.layout.fragment_auth, container, false);

        ImageView iconView = baseView.findViewById(R.id.icon);
        iconView.setImageResource(sourceInfo.getIconId());

        // Replace placeholder text with actual cloud name
        String target = "Cloud";
        String replacement = getString(sourceInfo.getTextId());
        for (int id : TEXT_VIEW_IDS) {
            TextView textView = baseView.findViewById(id);
            Util.textViewReplace(textView, target, replacement);
        }

        Button button = baseView.findViewById(R.id.button);
        button.setOnClickListener(this);

        return baseView;
    }

    @Override
    public void onClick(View view) {
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = builder.build();
//        customTabsIntent.launchUrl(getContext(), Uri.parse(authUrl));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(authUrl));
        startActivity(intent);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
