import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface FetchState {
  deviceStatus: any;
  isLoadingCharging: any;
  isGotDeviceStatus: any;
  uiNightMode: any;
  errorStart: string;
  errorCharging: string;
}

export const initialState: FetchState = {
  deviceStatus: null,
  isLoadingCharging: null,
  isGotDeviceStatus: null,
  uiNightMode: null,
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
    deviceStatusUiMode(state: FetchState, action: PayloadAction<any>) {
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
  },
});

export const { setDeviceStatusUndefind } = FetchSlice.actions;

export default FetchSlice.reducer;
