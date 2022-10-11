import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface FetchState {
  deviceStatus: any;
  isDeviceOnline: any;
  isLoadingCharging: any;
  isGotDeviceStatus: any;
  isModalOpen: any;
  uiNightMode: any;
  errorStart: string;
  errorCharging: string;
}

export const initialState: FetchState = {
  deviceStatus: null,
  isDeviceOnline: null,
  isLoadingCharging: false,
  isGotDeviceStatus: false,
  isModalOpen: false,
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
    uiNightModeGet(state: FetchState, action: PayloadAction<any>) {
      state.uiNightMode = action.payload;
    },
    deviceStatusFetchingError(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.errorCharging = action.payload;
    },
    setDeviceStatusUndefind(state: FetchState, action: PayloadAction<any>) {
      state.deviceStatus = action.payload;
    },
    deviceOnlineStatus(state: FetchState, action: PayloadAction<any>) {
      state.isDeviceOnline = action.payload;
    },
    setModalOpen(state: FetchState, action: PayloadAction<any>) {
      state.isModalOpen = action.payload;
    },
  },
});

export const { setDeviceStatusUndefind, setModalOpen, deviceOnlineStatus } = FetchSlice.actions;

export default FetchSlice.reducer;
