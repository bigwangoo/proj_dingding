package com.tianxiabuyi.txutils_ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils_ui.R;

/**
 * Created by ASUS on 2016/8/3.
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private View customView;
        private int messageTextColor;
        private int positiveTextColor;
        private int negativeTextColor;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private int mMesagePaddingLeft;
        private int mMesagePaddingRight;
        private int mMesagePaddingTop;
        private int mMesagePaddingBottom;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setMessagePadding(int left, int top, int right, int bottom) {
            mMesagePaddingLeft = left;
            mMesagePaddingRight = right;
            mMesagePaddingTop = top;
            mMesagePaddingBottom = bottom;
            return this;
        }


        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveTextColor(int positiveTextColor) {
            this.positiveTextColor = positiveTextColor;
            return this;
        }

        public Builder setNegativeTextColor(int negativeTextColor) {
            this.negativeTextColor = negativeTextColor;
            return this;
        }

        public Builder setMessageTextColor(int messageTextColor) {
            this.messageTextColor = messageTextColor;
            return this;
        }

        public Builder addCustomView(View customView) {
            this.customView = customView;
            return this;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.CustomDialog);
            View layout = inflater.inflate(R.layout.tx_dialog_custom_layout, null);

            if (customView != null) {
                FrameLayout flContent = (FrameLayout) layout.findViewById(R.id.flContent);
                flContent.addView(customView);
            }

            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    1000, 600));
            // set the dialog title
            TextView tvTitle = (TextView) layout.findViewById(R.id.title);
            tvTitle.setText(title);
            // set the confirm button
            if (positiveButtonText != null) {
                Button btnPositive = (Button) layout.findViewById(R.id.positiveButton);
                btnPositive.setText(positiveButtonText);
                if (positiveTextColor != 0) {
                    btnPositive.setTextColor(context.getResources().getColor(positiveTextColor));
                }

                if (positiveButtonClickListener != null) {
                    btnPositive
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                Button btnNegative = (Button) layout.findViewById(R.id.negativeButton);
                btnNegative.setText(negativeButtonText);
                if (negativeTextColor != 0) {
                    btnNegative.setTextColor(context.getResources().getColor(negativeTextColor));
                }
                if (negativeButtonClickListener != null) {
                    btnNegative
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                TextView tvMessage = (TextView) layout.findViewById(R.id.message);
                tvMessage.setText(message);
                if (messageTextColor != 0) {
                    tvMessage.setTextColor(context.getResources().getColor(messageTextColor));
                }
            } else if (mMesagePaddingLeft != 0 || mMesagePaddingRight != 0 || mMesagePaddingTop != 0 || mMesagePaddingBottom != 0) {
                TextView tvMessage = (TextView) layout.findViewById(R.id.message);
                tvMessage.setPadding(mMesagePaddingLeft, mMesagePaddingTop, mMesagePaddingRight, mMesagePaddingBottom);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }

    public void show(OnDialogShowListener listener) {
        this.show();
        listener.showed();
    }

    public interface OnDialogShowListener {
        void showed();
    }
}
