import { Navigate, useSearchParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { useEffect } from "react";
import { getStationInfo } from "../../store/reducers/ActionCreators";

export default function PrivateRoute({ children }: { children: any }) {
  const dispatch = useAppDispatch();
  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);

  const [searchParams] = useSearchParams();
  let stationNumbers: any = searchParams.get("station");
  localStorage.setItem("stationNumber", stationNumbers ? stationNumbers : "2");

  useEffect(() => {
    dispatch(getStationInfo());
    console.log(deviceStatus?.state)
  }, []);

  return deviceStatus?.state === "IN_PROGRESS" ? <Navigate to={`/charging?station=${stationNumbers}`}/> : children;
}
