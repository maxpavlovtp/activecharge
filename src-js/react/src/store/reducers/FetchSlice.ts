import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface FetchState {
  deviceStatus: any;
  isDeviceOffline: any;
  isLoadingCharging: any;
  isGotDeviceStatus: any;
  uiNightMode: any;
  errorStart: string;
  errorCharging: string;
}

export const initialState: FetchState = {
  deviceStatus: null,
  isDeviceOffline: false,
  isLoadingCharging: false,
  isGotDeviceStatus: false,
  uiNightMode: false,
  errorStart: "",
  errorCharging: "",
};

export const FetchSlice = createSlice({
  name: "fetch",
  initialState,
  reducers: {
    chargingDataFetching(state: FetchState) {
      state.isLoadingCharging = true;
    },
    chargingDataFetchingSuccess(state: FetchState) {
      state.isLoadingCharging = false;
      state.errorStart = "";
    },
    chargingDataFetchingError(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.isLoadingCharging = false;
      state.errorStart = action.payload;
    },

    deviceStatusFetching(state: FetchState) {
      state.isGotDeviceStatus = true;
    },
    deviceStatusFetchingSuccess(state: FetchState, action: PayloadAction<any>) {
      state.isGotDeviceStatus = false;
      state.errorCharging = "";
      state.deviceStatus = action.payload;
    },
    deviceStatusFetchingError(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.errorCharging = action.payload;
    },

    uiNightModeGet(state: FetchState, action: PayloadAction<any>) {
      state.uiNightMode = action.payload;
    },
    deviceOfflineStatus(state: FetchState, action: PayloadAction<any>) {
      state.isDeviceOffline = action.payload;
    },
    setDeviceStatusUndefind(state: FetchState, action: PayloadAction<any>) {
      state.deviceStatus = action.payload;
    },
  },
});

export const { setDeviceStatusUndefind } = FetchSlice.actions;

export default FetchSlice.reducer;
