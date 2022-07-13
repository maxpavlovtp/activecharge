import { AppDispatch } from "../store";
import axios from "axios";
import { FetchSlice } from "./FetchSlice";

const urlOn = `${process.env.REACT_APP_LINK_SERVE}device/start`;
const urlV2Start = `http://localhost:8080/device/v2/start`;

const urlDeviceStatus = `${process.env.REACT_APP_LINK_SERVE}device/getDeviceStatus`;
const urlV2Status = `http://localhost:8080/device/v2/status?id=`;

const urlChargingStatus = `${process.env.REACT_APP_LINK_SERVE}device/getChargingStatus`;

export const idStart = () => async (dispatch: AppDispatch) => {
  const data = JSON.stringify({
    station_number: "2",
    period_s: 60,
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
    .then(function (response: any) {
      localStorage.setItem("idDevice", response.data ? response.data : null);
      console.log(JSON.stringify(response.data));
      console.log(localStorage.getItem("idDevice"));
    })
    .catch(function (error: any) {
      console.log(error);
      dispatch(FetchSlice.actions.chargingDataFetchingError(error.message));
    });
  dispatch(FetchSlice.actions.chargingDataFetchingSuccess());
};

// export const fetchChargingData = () => async (dispatch: AppDispatch) => {
//   try {
//     dispatch(FetchSlice.actions.chargingDataFetching());
//     let stationNumber = localStorage.getItem("stationNumber");
//     const response = await axios.get(urlOn + "?station=" + stationNumber);
//     dispatch(FetchSlice.actions.chargingDataFetchingSuccess(response.data));
//     console.log(response.data);
//   } catch (e: any) {
//     dispatch(FetchSlice.actions.chargingDataFetchingError(e.message));
//     console.log(e.message);
//   }
// };

export const getStationInfo = () => async (dispatch: AppDispatch) => {
  if (localStorage.getItem("idDevice") !== null) {
    try {
      dispatch(FetchSlice.actions.deviceStatusFetching());
      axios
        .get(urlV2Status + localStorage.getItem("idDevice"))
        .then(function (result: any) {
          dispatch(FetchSlice.actions.deviceStatusFetchingSuccess(result.data));
          console.log(result.data);
        });
    } catch (e: any) {
      dispatch(FetchSlice.actions.deviceStatusFetchingError(e.message));
      console.log(e.message);
    }
  }
};

export const getDeviceStatus = () => async (dispatch: AppDispatch) => {
  try {
    dispatch(FetchSlice.actions.deviceOLDStatusFetching());
    let stationNumber = localStorage.getItem("stationNumber");
    const response = await axios.get(
      urlDeviceStatus + "?station=" + stationNumber
    );
    dispatch(FetchSlice.actions.deviceOLDStatusFetchingSuccess(response.data));
    console.log(response.data);
  } catch (e: any) {
    dispatch(FetchSlice.actions.deviceOLDStatusFetchingError(e.message));
    console.log(e.message);
  }
};

export const getChargingStatus = () => async (dispatch: AppDispatch) => {
  try {
    dispatch(FetchSlice.actions.chargingStatusFetching());
    let stationNumber = localStorage.getItem("stationNumber");
    const response = await axios.get(
      urlChargingStatus + "?station=" + stationNumber
    );
    dispatch(
      FetchSlice.actions.chargingStatusFetchingSuccess(response.data.data)
    );
    console.log(response.data);
  } catch (e: any) {
    dispatch(FetchSlice.actions.chargingStatusFetchingError(e.message));
    console.log(e.message);
  }
};
