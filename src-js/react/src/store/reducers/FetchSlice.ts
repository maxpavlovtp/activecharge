import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { IFetch } from "../../interfaces";

interface FetchState {
  startDataOverload: IFetch[];
  startDataCharging: IFetch[];
  chargingStatus: number;
  isDeviceOn: boolean;
  isLoadingOverload: boolean;
  isLoadingCharging: boolean;
  isGotDeviceStatus: boolean;
  error: string;
}

const initialState: FetchState = {
  startDataOverload: [],
  startDataCharging: [],
  chargingStatus: 0,
  isDeviceOn: false,
  isLoadingOverload: false,
  isLoadingCharging: false,
  isGotDeviceStatus: false,
  error: "",
};

export const FetchSlice = createSlice({
  name: "fetch",
  initialState,
  reducers: {
    overloadDataFetching(state: FetchState) {
      state.isLoadingOverload = true;
    },
    overloadDataFetchingSuccess(
      state: FetchState,
      action: PayloadAction<IFetch[]>
    ) {
      state.isLoadingOverload = false;
      state.error = "";
      state.startDataOverload = action.payload;
    },
    overloadDataFetchingError(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.isLoadingOverload = false;
      state.error = action.payload;
    },

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
      state.isGotDeviceStatus = true;
    },
    chargingStatusFetchingSuccess(
      state: FetchState,
      action: PayloadAction<number>
    ) {
      state.isGotDeviceStatus = false;
      state.error = "";
      state.chargingStatus = action.payload;
    },
    chargingStatusFetchingError(
      state: FetchState,
      action: PayloadAction<string>
    ) {
      state.isGotDeviceStatus = false;
      state.error = action.payload;
    },

    deviceOnStatus(state: FetchState, action: PayloadAction<boolean>) {
      state.isDeviceOn = action.payload;
    },
  },
});

export default FetchSlice.reducer;
