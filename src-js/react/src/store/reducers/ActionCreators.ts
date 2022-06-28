import { AppDispatch } from "../store";
import axios from "axios";
import { FetchSlice } from "./FetchSlice";

const urlOn = `${process.env.REACT_APP_LINK_SERVE}device/start`;
const urlDeviceStatus = `${process.env.REACT_APP_LINK_SERVE}device/getDeviceStatus`
const urlIsDeviceOn = `${process.env.REACT_APP_LINK_SERVE}device/isDeviceOn`;
const urlChargingStatus = `${process.env.REACT_APP_LINK_SERVE}device/getChargingStatus`;
const urlPower = `${process.env.REACT_APP_LINK_SERVE}device/getPower`;


export const fetchChargingData = () => async (dispatch: AppDispatch) => {
  try {
    dispatch(FetchSlice.actions.chargingDataFetching());
    const response = await axios.get(urlOn);
    dispatch(FetchSlice.actions.chargingDataFetchingSuccess(response.data));
    console.log(response.data);
  } catch (e: any) {
    dispatch(FetchSlice.actions.chargingDataFetchingError(e.message));
    console.log(e.message);
  }
};


export const getDeviceStatus = () => async (dispatch: AppDispatch) => {
  try {
    dispatch(FetchSlice.actions.deviceStatusFetching());
    const response = await axios.get(urlDeviceStatus);
    dispatch(FetchSlice.actions.deviceStatusFetchingSuccess(response.data));
    console.log(response.data);
  } catch (e: any) {
    dispatch(FetchSlice.actions.deviceStatusFetchingError(e.message));
    console.log(e.message);
  }
};

export const getPower = () => async (dispatch: AppDispatch) => {
  try {
    dispatch(FetchSlice.actions.devicePowerFetching());
    const response = await axios(urlPower);
    dispatch(FetchSlice.actions.devicePowerFetchingSuccess(response.data.data));
    console.log(response.data);
  } catch (e: any) {
    dispatch(FetchSlice.actions.devicePowerFetchingError(e.message));
    console.log(e.message);
  }
};

export const getChargingStatus = () => async (dispatch: AppDispatch) => {
  try {
    dispatch(FetchSlice.actions.chargingStatusFetching());
    const response = await axios.get(urlChargingStatus);
    dispatch(
      FetchSlice.actions.chargingStatusFetchingSuccess(response.data.data)
    );
    console.log(response.data);
  } catch (e: any) {
    dispatch(FetchSlice.actions.chargingStatusFetchingError(e.message));
    console.log(e.message);
  }
};
