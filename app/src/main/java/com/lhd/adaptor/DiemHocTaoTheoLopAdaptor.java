package com.lhd.adaptor;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;
import com.lhd.activity.MainActivity;
import com.lhd.fragment.DiemHocTapTheoLopFragment;
import com.lhd.object.ItemBangDiemThanhPhan;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.object.UIFromHTML;
import com.lhd.uneti.R;

import java.util.ArrayList;
import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;
import static com.lhd.activity.MainActivity.isOnline;

/**
 * Created by d on 25/01/2017.
 */

public class DiemHocTaoTheoLopAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private DiemHocTapTheoLopFragment diemHocTapTheoLopFragment;
    private ArrayList<ItemBangDiemThanhPhan> bangDiemThanhPhan;
    private ItemBangKetQuaHocTap itemBangKetQuaHocTap;
    private Activity activity;
    private RecyclerView recyclerView;
    private List<Object> mRecyclerViewItems;
    private AlertDialog.Builder builder;
    class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        public NativeExpressAdView nativeExpressAdView;
        public NativeExpressAdViewHolder(View view) {
            super(view);
            this.nativeExpressAdView= (NativeExpressAdView) view.findViewById(R.id.ads_navite_to);
        }
    }
    class ItemDiemThanhPhan extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView tvTenSV;
        TextView tvMaSV;
        TextView tvD1;
        TextView tvD2;
        TextView tvD3;
        TextView tvD4;
        TextView tvSoTietNghi;
        TextView tvDTB;
        TextView tvDieuKien;
        TextView stt;
        public ItemDiemThanhPhan(View itemView) {
            super(itemView);
            this.tvTenSV = (TextView) itemView.findViewById(R.id.id_item_diem_sv_tensv);
            this.tvMaSV = (TextView) itemView.findViewById(R.id.id_item_diem_sv_masv);
            this.tvD1 = (TextView) itemView.findViewById(R.id.id_item_diem_sv_d1);
            this.tvD2 = (TextView) itemView.findViewById(R.id.id_item_diem_sv_d2);
            this.tvD3 = (TextView) itemView.findViewById(R.id.id_item_diem_sv_d3);
            this.tvD4 = (TextView) itemView.findViewById(R.id.id_item_diem_sv_d4);
            this.tvSoTietNghi = (TextView) itemView.findViewById(R.id.id_item_diem_sv_so_tiet_nghi);
            this.tvDTB = (TextView) itemView.findViewById(R.id.id_item_diem_sv_dtb);
            this.tvDieuKien = (TextView) itemView.findViewById(R.id.id_item_diem_sv_dieuKien);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_diem_sv_stt);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position==0) return MENU_ITEM_VIEW_TYPE;
        return (position % ITEMS_PER_AD == 0&&isOnline(diemHocTapTheoLopFragment.getActivity())) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
    }

    public DiemHocTaoTheoLopAdaptor(List<Object> mRecyclerViewItems, RecyclerView recyclerView,
                                 Activity activity, ItemBangKetQuaHocTap itemBangKetQuaHocTap,
                                 ArrayList<ItemBangDiemThanhPhan> bangDiemThanhPhan,
                                 DiemHocTapTheoLopFragment diemHocTapTheoLopFragment) {
        this.recyclerView = recyclerView;
        this.mRecyclerViewItems = mRecyclerViewItems;
        this.activity = activity;
        this.itemBangKetQuaHocTap = itemBangKetQuaHocTap;
        this.bangDiemThanhPhan = bangDiemThanhPhan;
        this.diemHocTapTheoLopFragment = diemHocTapTheoLopFragment;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_express_ad_to, parent, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
            default:
            case MENU_ITEM_VIEW_TYPE:
                View view = LayoutInflater.from(activity).inflate(R.layout.item_diem_sinh_vien, parent, false);
                ItemDiemThanhPhan holder = new ItemDiemThanhPhan(view);
                return holder;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                NativeExpressAdViewHolder nativeExpressAdViewHolder= (NativeExpressAdViewHolder) holder;
                diemHocTapTheoLopFragment.loadNativeExpressAt(nativeExpressAdViewHolder.nativeExpressAdView);
                break;
            default: case MainActivity.MENU_ITEM_VIEW_TYPE:
                ItemDiemThanhPhan itemDiemThanhPhan= (ItemDiemThanhPhan) holder;
                final ItemBangDiemThanhPhan itemBangDiemThanhPhan= (ItemBangDiemThanhPhan) mRecyclerViewItems.get(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder = new AlertDialog.Builder(activity);
                        builder.setTitle(itemBangDiemThanhPhan.getTenSv());
                        final String [] list={" Xem thông tin","Xem điểm "+itemBangKetQuaHocTap.getTenMon()};
                        builder.setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i==0){
                                    Intent returnIntent=activity.getIntent();
                                    returnIntent.putExtra(MainActivity.MA_SV,itemBangDiemThanhPhan.getMsv());
                                    activity.setResult(Activity.RESULT_OK,returnIntent);
                                    Log.e("ListActivity",itemBangDiemThanhPhan.getMsv());
                                    activity.finish();
                                    activity.overridePendingTransition(R.anim.left_end, R.anim.right_end);
                                }else{
                                    diemHocTapTheoLopFragment.showAlert("Kết quả học tập "+itemBangKetQuaHocTap.getTenMon(),
                                            UIFromHTML.uiDiemMonListAc(itemBangDiemThanhPhan),itemBangKetQuaHocTap.getTenMon(),
                                            itemBangDiemThanhPhan.toString(),activity);
                                }
                            }
                        });
                        builder.show();
                    }
                });

                itemDiemThanhPhan.tvMaSV.setText(itemBangDiemThanhPhan.getMsv());
                itemDiemThanhPhan.tvTenSV.setText(itemBangDiemThanhPhan.getTenSv());
                itemDiemThanhPhan.tvD1.setText(itemBangDiemThanhPhan.getD1());
                itemDiemThanhPhan.tvD2.setText(itemBangDiemThanhPhan.getD2());
                itemDiemThanhPhan.tvD3.setText(itemBangDiemThanhPhan.getD3());
                itemDiemThanhPhan.tvD4.setText(itemBangDiemThanhPhan.getD4());
                itemDiemThanhPhan.tvDieuKien.setText(itemBangDiemThanhPhan.getDieuKien());
                itemDiemThanhPhan.tvSoTietNghi.setText(itemBangDiemThanhPhan.getSoTietNghi());
                itemDiemThanhPhan.tvDTB.setText(itemBangDiemThanhPhan.getdTB());
                itemDiemThanhPhan.stt.setText(""+(bangDiemThanhPhan.indexOf(itemBangDiemThanhPhan)+1));
                break;
        }

    }
    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }
}
