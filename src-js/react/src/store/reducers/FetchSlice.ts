import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface FetchState {
  deviceStatus: any;
  isLoadingCharging: boolean;
  isGotDeviceStatus: boolean;
  errorStart: string;
  errorCharging: string;
}

export const initialState: FetchState = {
  deviceStatus: null,
  isLoadingCharging: false,
  isGotDeviceStatus: false,
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
    setDeviceStatusUndefind(state: FetchState, action: PayloadAction<any>) {
      state.deviceStatus = action.payload;
    },
  },
});

export const { setDeviceStatusUndefind } = FetchSlice.actions;

export default FetchSlice.reducer;
