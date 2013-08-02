package net.alphaDev.Decider.Fragments;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import net.alphaDev.Decider.Actions.DialogCancelledAction;
import net.alphaDev.Decider.R;
import android.view.View.OnClickListener;

public class AboutFragment
        extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity context = getActivity();
		final LayoutInflater inflater = LayoutInflater.from(context);
		final View view = inflater.inflate(R.layout.about_dialog, null);

		final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(R.string.about_title);
		dialog.setView(view);
		dialog.setCancelable(true);
		dialog.setNegativeButton(R.string.close, new DialogCancelledAction());
		return dialog.create();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	    setHandlers(view);
	}


	private void setHandlers(View view) {
		Intent httpHandler = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + getString(R.string.about_http)));
		View http = view.findViewById(R.id.about_http);
		http.setOnClickListener(getHandler(httpHandler));

		Intent iconHandler = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wpclipart.com/recreation/games/pool/eight_ball_large.png.html"));
		View icon = view.findViewById(R.id.about_icon_link);
		icon.setOnClickListener(getHandler(iconHandler));

		String ver = getString(R.string.app_name) + " " + getString(R.string.app_version);
		Intent mailHandler = new Intent(Intent.ACTION_SEND).setType("plain/text")
			.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.about_mail) })
			.putExtra(Intent.EXTRA_SUBJECT, ver);
		View mail = view.findViewById(R.id.about_mail);
		mail.setOnClickListener(getHandler(mailHandler));
	}

	private View.OnClickListener getHandler(final Intent intent) {
		return new View.OnClickListener() {
			public void onClick(View p1) {
				startActivity(intent);
			}
		};
	}
}
