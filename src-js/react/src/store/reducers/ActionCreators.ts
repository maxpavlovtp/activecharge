import { AppDispatch } from "../store";
import axios from "axios";
import { FetchSlice } from "./FetchSlice";
import { IFetch } from "./../../models/IFetch";

const urlOn = `${process.env.REACT_APP_LINK_SERVE}device/start`;
const urlIsDeviceOn = `${process.env.REACT_APP_LINK_SERVE}device/isDeviceOn`;
const urlChargingStatus = `${process.env.REACT_APP_LINK_SERVE}device/getChargingStatus`;

export const fetchOverloadData = () => async (dispatch: AppDispatch) => {
  try {
    dispatch(FetchSlice.actions.overloadDataFetching());
    const response = await axios.get(urlOn);
    dispatch(FetchSlice.actions.overloadDataFetchingSuccess(response.data));
    console.log(response);
  } catch (e: any) {
    dispatch(FetchSlice.actions.overloadDataFetchingError(e.message));
    console.log(e.message);
  }
};

export const fetchChargingData = () => async (dispatch: AppDispatch) => {
  try {
    dispatch(FetchSlice.actions.chargingDataFetching());
    const response = await axios.get(urlOn);
    dispatch(FetchSlice.actions.chargingDataFetchingSuccess(response.data));
    console.log(response);
  } catch (e: any) {
    dispatch(FetchSlice.actions.chargingDataFetchingError(e.message));
    console.log(e.message);
  }
};

export const getChargingStatus = () => async (dispatch: AppDispatch) => {
  try {
    dispatch(FetchSlice.actions.chargingStatusFetching());
    const response = await axios.get(urlChargingStatus);
    dispatch(FetchSlice.actions.chargingStatusFetchingSuccess(response.data.data));
    console.log(response);
  } catch (e: any) {
    dispatch(FetchSlice.actions.chargingStatusFetchingError(e.message));
    console.log(e.message);
  }
}

export const getDeviceIsOnStatus = () => async (dispatch: AppDispatch) => {
  try {
    const response = await axios.get(urlIsDeviceOn);
    dispatch(
      FetchSlice.actions.deviceOnStatus(response.data.data)
    );
    console.log(response);
  } catch (e: any) {
    console.log(e.message);
  }
};
