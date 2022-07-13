import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { IFetch } from "../../interfaces";

interface FetchState {
  startDataCharging: any;
  deviceStatus: any;
  deviceStatusOLD: any
  whichStation: any;
  devicePower: string;
  chargingStatus: number;
  isLoadingCharging: boolean;
  isGotChargingStatus: boolean;
  isGotDeviceStatus: boolean;
  isGotOLDDeviceStatus: boolean;
  error: string;
}

const initialState: FetchState = {
  startDataCharging: null,
  deviceStatus: null,
  deviceStatusOLD: null,
  devicePower: "",
  whichStation: null,
  chargingStatus: 0,
  isLoadingCharging: false,
  isGotChargingStatus: false,
  isGotDeviceStatus: false,
  isGotOLDDeviceStatus: false,
  error: ""
};

export const FetchSlice = createSlice({
  name: "fetch",
  initialState,
  reducers: {
    chargingDataFetching(state: FetchState) {
      state.isLoadingCharging = true;
    },
    chargingDataFetchingSuccess(
      state: FetchState,
    ) {
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

    chargingStatusFetching(state: FetchState) {
      state.isGotChargingStatus = true;
    },
    chargingStatusFetchingSuccess(
      state: FetchState,
      action: PayloadAction<number>
    ) {
      state.isGotChargingStatus = false;
      state.error = "";
      state.chargingStatus = action.payload;
    },
    chargingStatusFetchingError(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.isGotChargingStatus = false;
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

    deviceOLDStatusFetching(state: FetchState) {
      state.isGotOLDDeviceStatus = true;
    },
    deviceOLDStatusFetchingSuccess(state: FetchState, action: PayloadAction<any>) {
      state.isGotOLDDeviceStatus = false;
      state.error = "";
      state.deviceStatusOLD = action.payload;
    },
    deviceOLDStatusFetchingError(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.error = action.payload;
    },
  },
});

export default FetchSlice.reducer;
