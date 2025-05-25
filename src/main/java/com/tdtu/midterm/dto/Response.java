package com.tdtu.midterm.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response {
    private Integer code;
    private String status;
    private String message;
    private Object data;

    @Data
    public static class PageData {
        private Object content;
        private Long totalElements;
        private Integer totalPages;
        private Integer currentPage;
        private Integer pageSize;

        public static PageData of(Object content, Long totalElements, Integer totalPages, Integer currentPage, Integer pageSize) {
            PageData pageData = new PageData();
            pageData.setContent(content);
            pageData.setTotalElements(totalElements);
            pageData.setTotalPages(totalPages);
            pageData.setCurrentPage(currentPage);
            pageData.setPageSize(pageSize);
            return pageData;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Object data) {
        Builder builder = new Builder();
        builder.setData(data);
        return builder;
    }

    public static class Builder {
        private Integer code;
        private String status;
        private String message;
        private Object data;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        void setData(Object data) {
            this.data = data;
        }

        public Response build() {
            Response response = new Response();

            if (this.code == null) {
                this.code = 200;
            }
            response.setCode(code);

            if (this.status == null) {
                this.status = HttpStatus.OK.getReasonPhrase();
            }
            response.setStatus(status);

            if (this.message != null) {
                response.setMessage(message);
            } else {
                response.setMessage("");
            }

            if (this.data != null) {
                response.setData(data);
            } else {
                response.setData(null);
            }

            return response;
        }
    }
}
