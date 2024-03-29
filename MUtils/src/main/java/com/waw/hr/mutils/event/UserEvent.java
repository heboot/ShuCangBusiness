package com.waw.hr.mutils.event;

import com.waw.hr.mutils.bean.AddressBean;

public class UserEvent {

    public static class ChooseAddressEvent {

        private AddressBean addressBean;

        public ChooseAddressEvent(AddressBean addressBean) {
            this.addressBean = addressBean;
        }

        public AddressBean getAddressBean() {
            return addressBean;
        }
    }

    public static class WxPayEvent {

        private int errorCode;

        public WxPayEvent(int errorCode) {
            this.errorCode = errorCode;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }

}
