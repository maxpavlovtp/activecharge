import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getDeviceIsOnStatus } from "../../store/reducers/ActionCreators";
import Spinner from "../spinner/Spinner";

export default function PrivateRoute({ children }: { children: any }) {
  const [loading, setLoading] = useState<boolean>(true);
  const { isDeviceOn } = useAppSelector((state) => state.fetchReducer);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getDeviceIsOnStatus());
    setLoading(false);
  }, [dispatch]);

  if (loading) return <Spinner />;
  return !isDeviceOn ? children : <Navigate to="/charging" />;
}
