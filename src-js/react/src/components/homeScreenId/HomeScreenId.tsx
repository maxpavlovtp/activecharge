import { Navigate } from "react-router-dom";

export default function PrivateRoute({ children }: { children: any }) {
  return <Navigate to="/2" />
}
