import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { IFetch } from "../../interfaces";

interface FetchState {
  deviceStatus: any;
  isLoadingCharging: boolean;
  isGotDeviceStatus: boolean;
  error: string;
}

const initialState: FetchState = {
  deviceStatus: null,
  isLoadingCharging: false,
  isGotDeviceStatus: false,
  error: "",
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
      state.error = "";
    },
    chargingDataFetchingError(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.isLoadingCharging = false;
      state.error = action.payload;
    },

    deviceStatusFetching(state: FetchState) {
      state.isGotDeviceStatus = true;
    },
    deviceStatusFetchingSuccess(state: FetchState, action: PayloadAction<any>) {
      state.isGotDeviceStatus = false;
      state.error = "";
      state.deviceStatus = action.payload;
    },
    deviceStatusFetchingError(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.error = action.payload;
    },

    setDeviceStatusUndefind(state: FetchState, action: PayloadAction<any>) {
      state.deviceStatus = action.payload;
    },
  },
});

export const { setDeviceStatusUndefind } = FetchSlice.actions;

export default FetchSlice.reducer;
