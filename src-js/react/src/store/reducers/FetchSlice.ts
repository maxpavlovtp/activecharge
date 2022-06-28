import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { IFetch } from "../../interfaces";

interface FetchState {
  startDataCharging: IFetch[];
  deviceStatus: any;
  devicePower: string;
  chargingStatus: number;
  isDeviceOn: boolean;
  isLoadingCharging: boolean;
  isGotDevicePower: boolean;
  isGotChargingStatus: boolean;
  isGotDeviceStatus: boolean;
  error: string;
}

const initialState: FetchState = {
  startDataCharging: [],
  deviceStatus: null,
  devicePower: "",
  chargingStatus: 0,
  isDeviceOn: false,
  isLoadingCharging: false,
  isGotDevicePower: false,
  isGotChargingStatus: false,
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
    chargingDataFetchingSuccess(
      state: FetchState,
      action: PayloadAction<IFetch[]>
    ) {
      state.isLoadingCharging = false;
      state.error = "";
      state.startDataCharging = action.payload;
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

    devicePowerFetching(state: FetchState) {
      state.isGotDevicePower = true;
    },
    devicePowerFetchingSuccess(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.isGotDevicePower = false;
      state.error = "";
      state.devicePower = action.payload;
    },
    devicePowerFetchingError(state: FetchState, action: PayloadAction<string>) {
      state.isGotDevicePower = false;
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
  },
});

export default FetchSlice.reducer;
