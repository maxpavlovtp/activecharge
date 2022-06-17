import React from "react";
import { Link } from "react-router-dom";
import { useAppSelector } from "../../hooks/reduxHooks";

export default function PrivateRoute({ children }: { children: any }) {
  const { isDeviceOn } = useAppSelector((state) => state.fetchReducer);
  console.log('isDeviceOn: ' + isDeviceOn);
  return isDeviceOn === false ? children : <Link to='/charging' />
}
