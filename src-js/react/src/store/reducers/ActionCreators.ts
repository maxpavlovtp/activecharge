import { AppDispatch } from "../store";
import axios from "axios";
import { FetchSlice, setDeviceStatusUndefind } from "./FetchSlice";

const urlV2Start = `${process.env.REACT_APP_LINK_SERVE}device/v2/start`;
const urlV2Status = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/status?`;
const urlStationIsOnline = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/isOnline?`;

export const getDeviceFingerPrint = () => {
  const userUID = localStorage.getItem("@fpjs@client@__null__null__false");
  const parsedUID = JSON.parse(userUID as string);
  return userUID ? parsedUID.body.visitorId : "";
};

export const idStart = (station: string) => async (dispatch: AppDispatch) => {
  dispatch(setDeviceStatusUndefind(undefined));
  const deviceFingerPrint = getDeviceFingerPrint();
  const data = JSON.stringify({
    station_number: station,
    device_finger_print: deviceFingerPrint,
  });

  const config = {
    method: "post",
    url: urlV2Start,
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    data: data,
  };
  dispatch(FetchSlice.actions.chargingDataFetching());
  await axios(config)
    .catch(function (error: any) {
      dispatch(FetchSlice.actions.chargingDataFetchingError(error.message));
      console.log(error.message);
    })
    .then(function (response: any) {
      localStorage.setItem(
        "interval",
        response.data ? response.data.scan_interval_ms : 2000
      );
      console.log(JSON.stringify(response.data));
      dispatch(FetchSlice.actions.chargingDataFetchingSuccess());
    });
};

export const getStationInfo =
  (station: string) => async (dispatch: AppDispatch) => {
    const deviceFingerPrint = getDeviceFingerPrint();
    dispatch(FetchSlice.actions.deviceStatusFetching());
    await axios
      .get(
        `${urlV2Status}station_number=${station}&device_finger_print=${deviceFingerPrint}`
      )

      .catch(function (error: any) {
        dispatch(FetchSlice.actions.deviceStatusFetchingError(error.message));
        console.log(error.message);
      })
      .then(function (result: any) {
        dispatch(FetchSlice.actions.deviceStatusFetchingSuccess(result.data));
        console.log(result.data);
      });
  };

export const getUiNightMode =
  (station: string) => async (dispatch: AppDispatch) => {
    const deviceFingerPrint = getDeviceFingerPrint();
    await axios
      .get(
        `${urlV2Status}station_number=${station}&device_finger_print=${deviceFingerPrint}`
      )
      .catch(function (error: any) {
        dispatch(FetchSlice.actions.deviceStatusFetchingError(error.message));
        console.log(error.message);
      })
      .then(function (result: any) {
        dispatch(FetchSlice.actions.uiNightModeGet(result.data.uiNightMode));
        console.log(result.data);
      });
  };

export const getDeviceOnlineStatus =
  (station: string) => async (dispatch: AppDispatch) => {
    const deviceFingerPrint = getDeviceFingerPrint();
    await axios
      .get(
        `${urlStationIsOnline}station_number=${station}&device_finger_print=${deviceFingerPrint}`
      )
      .catch(function (error: any) {
        dispatch(FetchSlice.actions.deviceStatusFetchingError(error.message));
        console.log(error.message);
      })
      .then(function (result: any) {
        dispatch(FetchSlice.actions.deviceOnlineStatus(result.data));
        console.log(result.data);
      });
  };
