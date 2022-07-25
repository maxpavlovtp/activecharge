import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface FetchState {
  deviceStatus: any;
  stationNumber: any;
  isLoadingCharging: boolean;
  isGotDeviceStatus: boolean;
  error: string;
}

export const initialState: FetchState = {
  deviceStatus: null,
  stationNumber: null,
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

    setStationNumber(state: FetchState, action: PayloadAction<number>) {
      state.stationNumber = action.payload;
    },
    
    setDeviceStatusUndefind(state: FetchState, action: PayloadAction<any>) {
      state.deviceStatus = action.payload;
    },
  },
});

export const { setStationNumber } = FetchSlice.actions;
export const { setDeviceStatusUndefind } = FetchSlice.actions;

export default FetchSlice.reducer;
