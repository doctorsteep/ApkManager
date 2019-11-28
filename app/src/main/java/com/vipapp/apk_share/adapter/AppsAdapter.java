package com.vipapp.apk_share.adapter;

import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.support.v7.util.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.vipapp.apk_share.*;
import com.vipapp.apk_share.data.*;
import com.vipapp.apk_share.model.*;
import java.util.*;
import org.vipapp.design.fm.util.FMTypeface;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> implements Filterable {

    Context context1;
    List<String> stringList;
	List<String> stringList2;
	Apk apk = new Apk();
	MainActivity m = new MainActivity();
	
    public AppsAdapter(Context context, List<String> list){
        context1 = context;
        stringList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout cardView;
        public ImageView imageView;
		public ImageView installedApp;
        public TextView textView_App_Name;
        public TextView textView_App_Package_Name;
		public TextView textView_App_Developer;
		
        public ViewHolder (View view){
            super(view);

            cardView = (LinearLayout) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.imageview);
			installedApp = (ImageView) view.findViewById(R.id.InstallegInGooglePlay);
            textView_App_Name = (TextView) view.findViewById(R.id.Apk_Name);
            textView_App_Package_Name = (TextView) view.findViewById(R.id.Apk_Package_Name);
			textView_App_Developer = (TextView) view.findViewById(R.id.App_Developer);
			
			
		}
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
       	View view2 = LayoutInflater.from(context1).inflate(R.layout.list_app, parent, false);
        ViewHolder viewHolder = new ViewHolder(view2);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position){

        ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(context1);

        final String ApplicationPackageName = (String) stringList.get(position);
        final String ApplicationLabelName = apkInfoExtractor.GetAppName(ApplicationPackageName);
        final Drawable drawable = apkInfoExtractor.getAppIconByPackageName(ApplicationPackageName);

		viewHolder.textView_App_Name.setTypeface(FMTypeface.getTypeface(context1, "medium"));
		
        viewHolder.textView_App_Name.setText(ApplicationLabelName);
        viewHolder.textView_App_Package_Name.setText(ApplicationPackageName);
		viewHolder.textView_App_Developer.setText(apkInfoExtractor.getAppDeveloper(ApplicationPackageName));
		
        viewHolder.imageView.setImageDrawable(drawable);
		
		try{
			viewHolder.imageView.setClipToOutline(true);
		} catch (Exception e) {}
		
		((Activity)context1).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						if (Data.verifyInstallerId(context1, ApplicationPackageName)) {
							viewHolder.installedApp.setVisibility(View.VISIBLE);
						} else {
							viewHolder.installedApp.setVisibility(View.GONE);
						}
					} catch (Exception e) {}
				}
			});

		
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					apk.setAppMenu(context1, ApplicationPackageName, 0);
					//Toast.makeText(context1, ApplicationPackageName, Toast.LENGTH_SHORT).show();
				}
			});
			
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
	
	@Override
	public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    stringList = stringList2;
                } else {
                    List<String> filteredList = new ArrayList<>();
                    for (String row : stringList2) {

                        // здесь мы отбираем нужные данные 
                        if (row.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    stringList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stringList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stringList = (ArrayList<String>) filterResults.values;

                // обновляем список отфильтрованных данных
                notifyDataSetChanged();
            }
        };
    }
}
