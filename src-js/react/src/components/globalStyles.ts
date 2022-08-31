import styled, { createGlobalStyle } from "styled-components";
import { Link } from "react-router-dom";
import { Nav } from "react-bootstrap";
export const GlobalStyles = createGlobalStyle`
  body {
    background: ${({ theme }: { theme: any }) => theme.body};
    color: ${({ theme }: { theme: any }) => theme.text};
    transition: all 0.50s linear;
  }
  `;

export const LinksColor = styled(Link)`
  color: ${({ theme }: { theme: any }) => theme.a};
  display: flex;
  text-decoration: none;
  &:hover {
    color: ${({ theme }: { theme: any }) => theme.a};
  }
  &:focus,
  &:hover,
  &:visited,
  &:link,
  &:active {
    text-decoration: none;
  }
  transition: all 0.5s linear;
`;
